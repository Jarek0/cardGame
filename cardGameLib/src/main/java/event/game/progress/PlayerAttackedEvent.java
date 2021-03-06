package event.game.progress;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.Card;

import static event.game.progress.GameProgressEventType.PLAYER_ATTACKED;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerAttackedEvent extends GameProgressEvent {

    private Card attackCard;

    private int attackerCardsCount;

    private String defenderLogin;

    public PlayerAttackedEvent(String gameId, String defenderLogin, Card attackCard, int attackerCardsCount) {
        super(gameId, PLAYER_ATTACKED);
        this.defenderLogin = defenderLogin;
        this.attackCard = attackCard;
        this.attackerCardsCount = attackerCardsCount;
    }
}
