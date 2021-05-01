package ru.itis.kpfu.kozlov.social_network_web.security.config;

import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.UserRepository;
import ru.itis.kpfu.kozlov.social_network_impl.services.UserServiceImpl;
import ru.itis.kpfu.kozlov.social_network_web.security.jwt.JwtConfigurer;
import ru.itis.kpfu.kozlov.social_network_web.security.jwt.JwtTokenFilter;
import ru.itis.kpfu.kozlov.social_network_web.security.jwt.JwtTokenProvider;
import ru.itis.kpfu.kozlov.social_network_web.security.oauth2.CustomOAuth2UserService;
import ru.itis.kpfu.kozlov.social_network_web.security.oauth2.OAuth2LoginSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@EnableWebSecurity
public class GlobalSecurityConfiguration {

    @Order(1)
    @Configuration
    public static class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @Autowired
        @Qualifier("jwtUserService")
        private UserDetailsService userDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
             System.out.println("hey");
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public FilterRegistrationBean<JwtTokenFilter> jwtAccessFilter(){
            FilterRegistrationBean<JwtTokenFilter> filterFilterRegistrationBean =
                    new FilterRegistrationBean<>();
            filterFilterRegistrationBean.setFilter(new JwtTokenFilter(jwtTokenProvider));
            filterFilterRegistrationBean.addUrlPatterns("/api/*", "/api/**");
            filterFilterRegistrationBean.setOrder(1);
            return filterFilterRegistrationBean;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            System.out.println("qwe");
            http
                    .antMatcher("/api/**")
                    .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                    .csrf()
                    .disable()
                    .httpBasic().disable()
                    .formLogin().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/*").authenticated()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/auth_api/login/").permitAll()
                    /*.and()
                    .apply(new JwtConfigurer(jwtTokenProvider))*/;
        }
    }


    @Order(2)
    @Configuration
    public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        @Qualifier("customUserDetailsService")
        private UserDetailsService userDetailsService;

        @Autowired
        private DataSource dataSource;

        @Autowired
        private CustomOAuth2UserService oAuth2UserService;

        @Autowired
        private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

        @Override
        public void configure(HttpSecurity http) throws Exception {
            System.out.println("heyhey");
            http.csrf().disable()
                    .oauth2Login().permitAll()
                    .loginPage("/auth")
                    .userInfoEndpoint().userService(oAuth2UserService)
                    .and()
                    .successHandler(oAuth2LoginSuccessHandler)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/posts").authenticated()
                    .antMatchers("/oauth2/**").anonymous()
                    .antMatchers("/admin").hasAuthority("ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/auth").permitAll()
                        .usernameParameter("email")
                        .passwordParameter("password")
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                                httpServletResponse.sendRedirect("/admin");
                            } else {
                                httpServletResponse.sendRedirect("/");
                            }

                        }
                    })
                    //.defaultSuccessUrl("/main")
                    .failureUrl("/auth?error")
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository());
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
            jdbcTokenRepository.setDataSource(dataSource);
            return jdbcTokenRepository;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }
    }
}