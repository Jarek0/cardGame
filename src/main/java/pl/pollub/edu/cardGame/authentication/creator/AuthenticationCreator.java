package pl.pollub.edu.cardGame.authentication.creator;

import command.RegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.pollub.edu.cardGame.authentication.domain.Authentication;
import pl.pollub.edu.cardGame.authentication.repository.AuthenticationRepository;

@Component
@RequiredArgsConstructor
public class AuthenticationCreator {

    private final AuthenticationRepository repository;

    private final BCryptPasswordEncoder encoder;

    public void create(RegisterCommand command) {
        Authentication auth = new Authentication(command, encoder);
        repository.save(auth);
    }

}
