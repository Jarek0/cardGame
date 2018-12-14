package pl.pollub.edu.cardGame.game.progress;

import lombok.RequiredArgsConstructor;
import model.Card;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.pollub.edu.cardGame.game.progress.attack.PlayerAttacker;
import pl.pollub.edu.cardGame.game.progress.defense.PlayerDefender;


@RestController
@Secured("ROLE_PLAYER")
@RequestMapping("/api/game/battle")
@RequiredArgsConstructor
public class GameProvider {

    private final PlayerAttacker playerAttacker;
    private final PlayerDefender playerDefender;

    @PostMapping("/attack/{gameId}")
    public void startAttack(@PathVariable String gameId, @RequestBody Card attackCard) {
        playerAttacker.attack(gameId, attackCard);
    }

    @PostMapping("/defense/{gameId}")
    public void startDefense(@PathVariable String gameId, @RequestBody Card defenseCard) {
        playerDefender.defense(gameId, defenseCard);
    }

    @DeleteMapping("/attack/{gameId}")
    public void stopAttack(@PathVariable String gameId) {
        playerAttacker.stopAttack(gameId);
    }

    @DeleteMapping("/defense/{gameId}")
    public void stopDefense(@PathVariable String gameId) {
        playerDefender.stopDefense(gameId);
    }

}
