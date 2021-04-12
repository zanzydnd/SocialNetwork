package ru.itis.kpfu.kozlov.social_network_web.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String name = oAuth2User.getName();
        String lastName = oAuth2User.getLastName();
        UserDto userDto = userService.findByEmail(email);
        if(userDto == null){
            userService.signUpAfterOAuth(email,name,lastName, UserEntity.AuthProvider.GOOGLE.toString());
        } else{
            userService.updateUserAfterOAuth(userDto, name, UserEntity.AuthProvider.GOOGLE.toString());
        }
        response.sendRedirect("/profile");
    }
}
