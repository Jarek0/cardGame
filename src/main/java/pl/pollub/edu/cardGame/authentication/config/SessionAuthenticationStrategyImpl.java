package pl.pollub.edu.cardGame.authentication.config;

import event.authentication.LogoutUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import pl.pollub.edu.cardGame.authentication.notifier.UserLogoutNotifier;
import pl.pollub.edu.cardGame.game.organization.destroyer.GameDestroyer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
public class SessionAuthenticationStrategyImpl implements SessionAuthenticationStrategy {

    private final SessionRegistry sessionRegistry;
    private final UserLogoutNotifier userLogoutNotifier;
    private final GameDestroyer gameDestroyer;

    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), true);
        for(SessionInformation session : sessions) {
            session.expireNow();
            userLogoutNotifier.notifyUserLogout(new LogoutUserEvent(authentication.getName()));
            gameDestroyer.destroyAllNotEndedGames(authentication.getName());
        }
        sessionRegistry.registerNewSession(request.getSession().getId(), authentication.getPrincipal());
    }

}
