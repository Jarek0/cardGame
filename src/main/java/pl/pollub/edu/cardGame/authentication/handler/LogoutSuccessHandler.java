package pl.pollub.edu.cardGame.authentication.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import pl.pollub.edu.cardGame.game.organization.destroyer.GameDestroyer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private final GameDestroyer gameDestroyer;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String username = authentication.getName();
        gameDestroyer.destroyAllNotEndedGames(username);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        response.getWriter().flush();
    }
}