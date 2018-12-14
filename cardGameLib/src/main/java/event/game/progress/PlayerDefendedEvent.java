package event.game.progress;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.Card;
import model.CardValue;

import java.util.Set;

import static event.game.progress.GameProgressEventType.PLAYER_DEFENDED;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerDefendedEvent extends GameProgressEvent {

    private Card defenseCard;

    private int defenderCardsCount;

    private String attackerLogin;

    private Set<CardValue> cardsOnBattleGround;

    public PlayerDefendedEvent(String gameId, String attackerLogin, Card defenseCard, int defenderCardsCount, Set<CardValue> cardsOnBattleGround) {
        super(gameId, PLAYER_DEFENDED);
        this.attackerLogin = attackerLogin;
        this.defenseCard = defenseCard;
        this.defenderCardsCount = defenderCardsCount;
        this.cardsOnBattleGround = cardsOnBattleGround;
    }
}
