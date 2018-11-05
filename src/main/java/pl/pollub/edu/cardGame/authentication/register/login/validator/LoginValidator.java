package pl.pollub.edu.cardGame.authentication.register.login.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.authentication.repository.AuthenticationRepository;


@Component
@RequiredArgsConstructor
public class LoginValidator {

    private final AuthenticationContext authContext;

    private final AuthenticationRepository repository;

    public boolean isCurrentLoginSame(String login) {
        String currentLogin = authContext.getCurrentAuthLogin();
        return login.equals(currentLogin);
    }

    public boolean isLoginAlreadyUsed(String login) {
        return repository.findByLogin(login).isPresent();
    }

}
