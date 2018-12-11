package pl.pollub.edu.cardGame.game.progress.notifier;

import event.game.progress.PlayerDefendedEvent;
import event.game.progress.PlayerStopDefenseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerDefendedNotifier {

    private final SimpMessageSendingOperations sender;

    public void notifyPlayerDefended(PlayerDefendedEvent event) {
        sender.convertAndSendToUser(event.getAttackerLogin(), event.getDestination(), event);
    }

    public void notifyPlayerStopDefense(List<PlayerStopDefenseEvent> events) {
        for(PlayerStopDefenseEvent event : events) {
            sender.convertAndSendToUser(event.getDestinationPlayer(), event.getDestination(), event);
        }
    }

}
