package event.game.progress;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.Card;

import static event.game.progress.GameProgressEventType.PLAYER_DEFENDED;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerDefendedEvent extends GameProgressEvent {

    private Card defenseCard;

    private String attackerLogin;

    public PlayerDefendedEvent(String gameId, String attackerLogin, Card defenseCard) {
        super(gameId, PLAYER_DEFENDED);
        this.attackerLogin = attackerLogin;
        this.defenseCard = defenseCard;
    }
}
