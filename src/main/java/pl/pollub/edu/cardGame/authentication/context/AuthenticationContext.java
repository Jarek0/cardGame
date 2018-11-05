package pl.pollub.edu.cardGame.authentication.context;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.pollub.edu.cardGame.authentication.domain.Authentication;


@Component
@RequiredArgsConstructor
public class AuthenticationContext {

    public Authentication getCurrentAuth() {
        return (Authentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getCurrentAuthLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
