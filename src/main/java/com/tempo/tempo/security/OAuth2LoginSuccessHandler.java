package com.tempo.tempo.security;

import com.tempo.tempo.entities.User;
import com.tempo.tempo.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public OAuth2LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String googleId = oAuth2User.getAttribute("sub");

        Optional<User> existingUser = userRepository.findByGoogleId(googleId);
        if (existingUser.isPresent()) {
            // User already exists, proceed with login
            User user = existingUser.get();
            // Set the user in the security context or perform any other necessary actions
        } else {
            // New user, create a new User entity and save it to the database
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setGoogleId(googleId);
            newUser.setUsername(generateUniqueUsername(email));
            // Save the new user to the database
            userRepository.save(newUser);
        }

        // super.onAuthenticationSuccess(request, response, authentication);
        response.sendRedirect("/dashboard"); // Redirect to app dashboard
    }
    // Generate a unique username based on the email
    private String generateUniqueUsername(String email) {
        String baseUsername = email.split("@")[0];
        String username = baseUsername;
        int count = 1;
        while (userRepository.findByUsername(username).isPresent()) {
            username = baseUsername + count;
            count++;
        }
        return username;
    }
}