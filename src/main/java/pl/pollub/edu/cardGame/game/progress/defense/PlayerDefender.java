package pl.pollub.edu.cardGame.game.progress.defense;

import event.game.progress.GameFinishedEvent;
import event.game.progress.PlayerDefendedEvent;
import event.game.progress.PlayerStopDefenseEvent;
import lombok.RequiredArgsConstructor;
import model.Card;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.game.domain.Game;
import pl.pollub.edu.cardGame.game.progress.notifier.PlayerDefendedNotifier;
import pl.pollub.edu.cardGame.game.progress.notifier.GameFinishedNotifier;
import pl.pollub.edu.cardGame.game.repository.GameRepository;

import java.util.List;

import static pl.pollub.edu.cardGame.game.organization.connection.exception.GameNotFoundException.startedGameNotFound;

@Service
@RequiredArgsConstructor
public class PlayerDefender {

    private final AuthenticationContext authContext;
    private final GameRepository gameRepository;
    private final PlayerDefendedNotifier defendedNotifier;
    private final GameFinishedNotifier gameFinishedNotifier;

    public void defense(String gameId, Card defenseCard) {
        ObjectId id = new ObjectId(gameId);
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> startedGameNotFound(id));
        String playerLogin = authContext.getCurrentAuthLogin();

        if(game.canDefense(playerLogin) && game.canCardDefense(defenseCard)) {
            if(game.didDefenderWonBeforeDefense()) {
                List<GameFinishedEvent> events = game.finish(playerLogin);
                gameFinishedNotifier.notifyGameFinished(events);
            }
            else {
                PlayerDefendedEvent event = game.defense(defenseCard);
                defendedNotifier.notifyPlayerDefended(event);
            }
        }
        gameRepository.save(game);
    }

    public void stopDefense(String gameId) {
        ObjectId id = new ObjectId(gameId);
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> startedGameNotFound(id));
        String playerLogin = authContext.getCurrentAuthLogin();

        if(game.canStopDefense(playerLogin)) {
            if(game.didAttackerWonBeforeStopDefense()) {
                List<GameFinishedEvent> events = game.finish(playerLogin);
                gameFinishedNotifier.notifyGameFinished(events);
            }
            else {
                List<PlayerStopDefenseEvent> events = game.stopDefense();
                defendedNotifier.notifyPlayerStopDefense(events);
            }
        }
        gameRepository.save(game);
    }

}
