package pl.pollub.edu.cardGame.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.edu.cardGame.game.domain.Game;
import pl.pollub.edu.cardGame.game.organization.destroyer.GameDestroyer;
import pl.pollub.edu.cardGame.game.repository.GameRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OnApplicationStartInactiveGameDestroyer implements ApplicationListener<ContextRefreshedEvent> {

    private final GameDestroyer gameDestroyer;

    private final GameRepository gameRepository;

    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        gameDestroyer.destroyAllNotEndedGames();
    }

}
