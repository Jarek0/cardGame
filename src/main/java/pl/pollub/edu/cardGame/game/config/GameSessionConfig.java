package pl.pollub.edu.cardGame.game.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.pollub.edu.cardGame.authentication.context.AuthenticationContext;
import pl.pollub.edu.cardGame.game.organization.destroyer.GameDestroyer;
import pl.pollub.edu.cardGame.game.repository.GameRepository;

import javax.servlet.http.HttpSessionListener;

@Configuration
public class GameSessionConfig {

    private final AuthenticationContext authContext;

    private final GameDestroyer gameDestroyer;

    @Autowired
    public GameSessionConfig(AuthenticationContext authContext, GameDestroyer gameDestroyer) {
        this.authContext = authContext;
        this.gameDestroyer = gameDestroyer;
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener()
    {
        return new ServletListenerRegistrationBean<>(new GameSessionListener(authContext, gameDestroyer));
    }
}
