package pl.pollub.edu.cardGame.game.organization;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.pollub.edu.cardGame.game.domain.Game;
import pl.pollub.edu.cardGame.game.domain.QGame;
import pl.pollub.edu.cardGame.game.organization.connection.appender.PlayerAppender;
import pl.pollub.edu.cardGame.game.organization.creator.GameCreator;
import pl.pollub.edu.cardGame.game.organization.destroyer.GameDestroyer;
import pl.pollub.edu.cardGame.game.organization.filter.GameFilter;
import pl.pollub.edu.cardGame.game.repository.GameRepository;
import response.PageResponse;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static pl.pollub.edu.cardGame.game.domain.GameStatus.OPEN;

@RestController
@Secured("ROLE_PLAYER")
@RequestMapping("/api/game/organization")
@RequiredArgsConstructor
public class GameOrganizer {


    private final GameCreator gameCreator;

    private final GameRepository gameRepository;

    private final GameDestroyer gameDestroyer;

    private final PlayerAppender appender;

    @PostMapping
    @Transactional
    public ResponseEntity organize() {
        gameDestroyer.destroyAllNotEndedGamesOfCurrentUser();
        gameCreator.create();

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{gameId}")
    @Transactional
    public void join(@PathVariable String gameId) {
        gameDestroyer.destroyAllNotEndedGamesOfCurrentUser();
        appender.join(gameId);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity close() {
        gameDestroyer.destroyAllNotEndedGamesOfCurrentUser();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/open")
    @Transactional(readOnly = true)
    public ResponseEntity getOpenGamesIds(Pageable pageable, GameFilter filter) {

        Page<Game> openGames;

        if(isNull(filter) || isNull(filter.getName()) || filter.getName().isEmpty()) {
            openGames = gameRepository.findOpenGames(pageable);
        }
        else {
            QGame game = QGame.game;
            BooleanExpression predicate = game.founderLogin.containsIgnoreCase(filter.getName());
            predicate.and(game.status.eq(OPEN));
            openGames = gameRepository.findAll(predicate, pageable);
        }

        PageResponse page = new PageResponse<>(
                openGames.getTotalPages(),
                openGames.getNumber(),
                openGames.getContent().stream().map(Game::toResponse).collect(Collectors.toList())
        );

        return ResponseEntity.ok(page);
    }

}
