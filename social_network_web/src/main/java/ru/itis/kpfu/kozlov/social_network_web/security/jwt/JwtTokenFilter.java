package ru.itis.kpfu.kozlov.social_network_web.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("do filter");
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        //SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        System.out.println(token);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            System.out.println("123123123");
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            if (auth != null) {
                System.out.println("12312312312333333");
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        System.out.println("12344444");
        filterChain.doFilter(req, res);
    }
}
