package pl.pollub.edu.cardGame.game.progress.notifier;

import event.game.progress.PlayerAttackedEvent;
import event.game.progress.PlayerStopAttackEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerAttackedNotifier {

    private final SimpMessageSendingOperations sender;

    public void notifyPlayerAttacked(PlayerAttackedEvent event) {
        sender.convertAndSendToUser(event.getDefenderLogin(), event.getDestination(), event);
    }

    public void notifyPlayerStopAttack(List<PlayerStopAttackEvent> events) {
        for(PlayerStopAttackEvent event : events) {
            sender.convertAndSendToUser(event.getDestinationPlayer(), event.getDestination(), event);
        }
    }

}
