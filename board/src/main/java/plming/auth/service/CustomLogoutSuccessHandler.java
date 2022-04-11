package plming.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        jwtTokenProvider.deleteTokenInCookie(response);
    }
}
