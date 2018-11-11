package pl.pollub.edu.cardGame.game.organization.connection.appender;

import event.GameStartedEvent;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.game.domain.Game;
import pl.pollub.edu.cardGame.game.organization.connection.notifier.GameConnectionNotifier;
import pl.pollub.edu.cardGame.game.repository.GameRepository;

import java.util.List;

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
        List<GameStartedEvent> events = game.joinPlayer(currentUserLogin);

        repository.save(game);

        notifier.notifyPlayersGameStarted(events);
    }

}
