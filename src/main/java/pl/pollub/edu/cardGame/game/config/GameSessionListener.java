package pl.pollub.edu.cardGame.game.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.game.domain.Game;
import pl.pollub.edu.cardGame.game.organization.destroyer.GameDestroyer;
import pl.pollub.edu.cardGame.game.repository.GameRepository;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameSessionListener implements HttpSessionListener {

    private final AuthenticationContext authContext;
    private final GameDestroyer gameDestroyer;

    private final int sessionTimeoutInSeconds = 300;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        String login = authContext.getCurrentAuthLogin();
        log.info("Session of user: " + login + " started");
        HttpSession session = event.getSession();
        session.setAttribute("login", login);
        session.setMaxInactiveInterval(sessionTimeoutInSeconds);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String login = event.getSession().getAttribute("login").toString();
        log.info("Session of user: " + login + " destroyed");
        gameDestroyer.destroyAllNotEndedGames(login);
    }

}
