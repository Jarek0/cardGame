package pl.pollub.edu.cardGame.game.organization.connection.notifier;

import event.game.organization.GameClosedEvent;
import event.game.organization.GameStartedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameConnectionNotifier {

    private final SimpMessageSendingOperations sender;

    public void notifyPlayersGameStarted(List<GameStartedEvent> events) {
        for(GameStartedEvent event : events) {
            sender.convertAndSendToUser(event.getPlayerLogin(), event.getDestination(), event);
        }
    }

    public void notifyPlayersGameClosed(GameClosedEvent event) {
        for(String playerLogin : event.getPlayers()) {
            sender.convertAndSendToUser(playerLogin, event.getDestination(), event);
        }
    }
}
