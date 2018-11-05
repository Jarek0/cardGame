package pl.pollub.edu.cardGame.authentication.register;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.pollub.edu.cardGame.authentication.creator.AuthenticationCreator;
import command.ChangeLoginCommand;
import command.RegisterCommand;
import pl.pollub.edu.cardGame.authentication.register.login.LoginChanger;
import pl.pollub.edu.cardGame.authentication.register.login.validator.LoginValidator;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication/registration")
@RequiredArgsConstructor
@Transactional
public class AuthenticationRegistration {

    private final AuthenticationCreator creator;

    private final LoginChanger loginChanger;

    private final LoginValidator validator;

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegisterCommand command) {
        if(loginChanger.isNotUsed(command.getLogin())) {
            creator.create(command);
        }
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_PLAYER")
    @PutMapping("/login")
    public ResponseEntity changeLogin(@Valid @RequestBody ChangeLoginCommand command) {
        String login = command.getLogin();

        if(validator.isCurrentLoginSame(login)) {
            return ResponseEntity.noContent().build();
        }

        if(loginChanger.isNotUsed(login)) {
            loginChanger.changeCurrentLogin(login);
        }

        return ResponseEntity.noContent().build();
    }
}
