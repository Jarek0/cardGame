package pl.pollub.edu.cardGame.game.organization.destroyer;

import event.GameClosedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.game.domain.Game;
import pl.pollub.edu.cardGame.game.organization.connection.notifier.GameConnectionNotifier;
import pl.pollub.edu.cardGame.game.repository.GameRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameDestroyer {

    private final AuthenticationContext authContext;

    private final GameRepository gameRepository;

    private final GameConnectionNotifier notifier;

    public void destroyAllNotEndedGamesOfCurrentUser() {
        String currentLogin = authContext.getCurrentAuthLogin();
        List<Game> games = gameRepository.findNotEndedByPlayerLogin(currentLogin);
        gameRepository.deleteAll(games);
        closeGames(games);
    }

    public void destroyAllNotEndedGames(String login) {
        List<Game> games = gameRepository.findNotEndedByPlayerLogin(login);
        gameRepository.deleteAll(games);
        closeGames(games);
    }

    public void destroyAllNotEndedGames() {
        List<Game> games = gameRepository.findNotEnded();
        gameRepository.deleteAll(games);
        closeGames(games);
    }

    private void closeGames(List<Game> games) {
        for(Game game : games) {
            GameClosedEvent event = game.close();
            notifier.notifyPlayersGameClosed(event);
        }
    }
}
