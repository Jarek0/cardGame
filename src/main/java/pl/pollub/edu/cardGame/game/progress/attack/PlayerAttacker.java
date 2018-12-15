package pl.pollub.edu.cardGame.game.progress.attack;

import event.game.progress.GameFinishedEvent;
import event.game.progress.PlayerAttackedEvent;
import event.game.progress.PlayerStopAttackEvent;
import lombok.RequiredArgsConstructor;
import model.Card;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.game.domain.Game;
import pl.pollub.edu.cardGame.game.progress.notifier.PlayerAttackedNotifier;
import pl.pollub.edu.cardGame.game.progress.notifier.GameFinishedNotifier;
import pl.pollub.edu.cardGame.game.ranking.RankingPositionService;
import pl.pollub.edu.cardGame.game.repository.GameRepository;

import java.util.List;

import static pl.pollub.edu.cardGame.game.organization.connection.exception.GameNotFoundException.startedGameNotFound;

@Service
@RequiredArgsConstructor
public class PlayerAttacker {

    private final AuthenticationContext authContext;
    private final GameRepository gameRepository;
    private final PlayerAttackedNotifier attackNotifier;
    private final GameFinishedNotifier gameFinishedNotifier;
    private final RankingPositionService rankingPositionService;

    public void attack(String gameId, Card attackCard) {
        ObjectId id = new ObjectId(gameId);
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> startedGameNotFound(id));
        String playerLogin = authContext.getCurrentAuthLogin();

        if(game.canAttack(playerLogin) && game.canCardAttack(attackCard)) {
            if(game.didAttackerWinBeforeAttack()) {
                List<GameFinishedEvent> events = game.finish(playerLogin);
                rankingPositionService.addPointsForPlayer(events.get(0));
                gameFinishedNotifier.notifyGameFinished(events);
            }
            else {
                PlayerAttackedEvent event = game.attack(attackCard);
                attackNotifier.notifyPlayerAttacked(event);
            }
        }
        gameRepository.save(game);
    }

    public void stopAttack(String gameId) {
        ObjectId id = new ObjectId(gameId);
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> startedGameNotFound(id));
        String playerLogin = authContext.getCurrentAuthLogin();

        if(game.canStopAttack(playerLogin)) {
            List<PlayerStopAttackEvent> events = game.stopAttack();
            attackNotifier.notifyPlayerStopAttack(events);
        }

        gameRepository.save(game);
    }

}
