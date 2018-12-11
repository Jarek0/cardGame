package pl.pollub.edu.cardGame.authentication.notifier;

import event.authentication.LogoutUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLogoutNotifier {

    private final SimpMessageSendingOperations sender;

    public void notifyUserLogout(LogoutUserEvent event) {
        sender.convertAndSendToUser(event.getUser(), event.getDestination(), event);
    }

}
