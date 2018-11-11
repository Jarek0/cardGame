package pl.pollub.edu.cardGame.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Component;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.game.organization.destroyer.GameDestroyer;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionListener extends HttpSessionEventPublisher {

    private final GameDestroyer gameDestroyer;

    private final int sessionTimeoutInSeconds = 1200;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        super.sessionCreated(event);
        log.info("Session of user started");
        HttpSession session = event.getSession();
        session.setMaxInactiveInterval(sessionTimeoutInSeconds);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        super.sessionDestroyed(event);
        String login = event.getSession().getAttribute("login").toString();
        log.info("Session of user: " + login + " destroyed");
        gameDestroyer.destroyAllNotEndedGames(login);
    }

}
