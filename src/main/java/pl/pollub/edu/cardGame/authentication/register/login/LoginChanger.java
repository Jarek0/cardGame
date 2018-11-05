package pl.pollub.edu.cardGame.authentication.register.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.authentication.domain.Authentication;
import pl.pollub.edu.cardGame.authentication.register.exception.LoginAlreadyUsedException;
import pl.pollub.edu.cardGame.authentication.register.login.validator.LoginValidator;
import pl.pollub.edu.cardGame.authentication.repository.AuthenticationRepository;


@Service
@RequiredArgsConstructor
public class LoginChanger {

    private final AuthenticationContext context;

    private final AuthenticationRepository repository;

    private final LoginValidator validator;

    public boolean isNotUsed(String login) {

        if(validator.isLoginAlreadyUsed(login)) {
            throw new LoginAlreadyUsedException(login);
        }

        return true;
    }

    public void changeCurrentLogin(String login) {
        Authentication auth = context.getCurrentAuth();
        auth.changeLogin(login);
        repository.save(auth);
    }

}
