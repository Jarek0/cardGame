package pl.pollub.edu.cardGame.authentication.password.matcher;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.pollub.edu.cardGame.authentication.domain.Authentication;

@Component
@RequiredArgsConstructor
public class PasswordMatcher {

    private final BCryptPasswordEncoder passwordEncoder;

    public boolean doesTheOldPasswordMatch(Authentication auth, String oldPassword) {
        return passwordEncoder.matches(oldPassword, auth.getPassword());
    }

}
