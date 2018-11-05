package pl.pollub.edu.cardGame.game.organization.connection.appender;

import event.PlayerJoinGameEvent;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.game.domain.Game;
import pl.pollub.edu.cardGame.game.organization.connection.notifier.GameConnectionNotifier;
import pl.pollub.edu.cardGame.game.repository.GameRepository;

import static pl.pollub.edu.cardGame.game.organization.connection.exception.GameNotFoundException.openGameNotFound;

@Service
@RequiredArgsConstructor
public class PlayerAppender {

    private final AuthenticationContext authContext;

    private final GameRepository repository;

    private final GameConnectionNotifier notifier;

    public void join(String gameId) {
        ObjectId id = new ObjectId(gameId);
        Game game = repository.findOpenById(id)
                .orElseThrow(() -> openGameNotFound(id));

        String currentUserLogin = authContext.getCurrentAuthLogin();
        PlayerJoinGameEvent event = game.joinPlayer(currentUserLogin);

        repository.save(game);

        notifier.notifyPlayerJoinToGame(event);
    }

}
