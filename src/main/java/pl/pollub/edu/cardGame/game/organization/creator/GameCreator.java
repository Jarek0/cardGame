package pl.pollub.edu.cardGame.game.organization.creator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.game.domain.Game;
import pl.pollub.edu.cardGame.game.repository.GameRepository;

@Service
@RequiredArgsConstructor
public class GameCreator {

    private final AuthenticationContext authContext;

    private final GameRepository repository;

    public Game create() {
        String currentUserLogin = authContext.getCurrentAuthLogin();
        Game game = new Game(currentUserLogin);
        return repository.save(game);
    }
}
