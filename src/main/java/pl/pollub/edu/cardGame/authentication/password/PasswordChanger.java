package pl.pollub.edu.cardGame.authentication.password;

import command.ChangePasswordCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.authentication.domain.Authentication;
import pl.pollub.edu.cardGame.authentication.password.exception.OldPasswordNotMatchException;
import pl.pollub.edu.cardGame.authentication.password.matcher.PasswordMatcher;
import pl.pollub.edu.cardGame.authentication.repository.AuthenticationRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication/password")
@RequiredArgsConstructor
public class PasswordChanger {

    private final PasswordMatcher passwordMatcher;

    private final AuthenticationContext authContext;

    private final AuthenticationRepository repository;

    private final BCryptPasswordEncoder encoder;

    @Secured("ROLE_PLAYER")
    @PutMapping
    @Transactional
    public ResponseEntity changePassword(@Valid @RequestBody ChangePasswordCommand command) {

        Authentication currentAuth = authContext.getCurrentAuth();

        if(!passwordMatcher.doesTheOldPasswordMatch(currentAuth, command.getOldPassword())) {
            throw new OldPasswordNotMatchException();
        }

        currentAuth.changePassword(encoder, command.getNewPassword());

        repository.save(currentAuth);

        return ResponseEntity.noContent().build();
    }
}
