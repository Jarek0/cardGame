package pl.pollub.edu.cardGame.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pl.pollub.edu.cardGame.game.organization.destroyer.GameDestroyer;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

    private final SessionRegistry sessionRegistry;
    private final GameDestroyer gameDestroyer;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        String username = event.getUser().getName();
        gameDestroyer.destroyAllNotEndedGames(username);
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(event.getUser(), true);
        for (SessionInformation session : sessions) {
            session.expireNow();
        }
    }
}
