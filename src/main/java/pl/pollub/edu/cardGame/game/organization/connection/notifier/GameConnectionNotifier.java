package pl.pollub.edu.cardGame.game.organization.connection.notifier;

import event.GameClosedEvent;
import event.PlayerJoinGameEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameConnectionNotifier {

    private final SimpMessageSendingOperations sender;

    public void notifyPlayerJoinToGame(PlayerJoinGameEvent event) {
        sender.convertAndSendToUser(event.getFounderLogin(), event.getDestination(), event);
    }

    public void notifyPlayersGameClosed(GameClosedEvent event) {
        for(String playerLogin : event.getPlayers()) {
            sender.convertAndSendToUser(playerLogin, event.getDestination(), event);
        }
    }
}
