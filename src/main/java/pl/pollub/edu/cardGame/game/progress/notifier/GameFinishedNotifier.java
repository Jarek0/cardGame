package pl.pollub.edu.cardGame.game.progress.notifier;

import event.game.progress.GameFinishedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameFinishedNotifier {

    private final SimpMessageSendingOperations sender;

    public void notifyGameFinished(List<GameFinishedEvent> events) {
        for(GameFinishedEvent event : events) {
            sender.convertAndSendToUser(event.getDestinationPlayer(), event.getDestination(), event);
        }
    }

}
