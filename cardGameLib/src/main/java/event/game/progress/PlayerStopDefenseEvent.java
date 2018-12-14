package event.game.progress;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.Card;

import java.util.ArrayList;
import java.util.List;

import static event.game.progress.GameProgressEventType.PLAYER_STOP_ATTACK;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerStopDefenseEvent extends GameProgressEvent implements NextRoundEvent {

    private String destinationPlayer;

    private ArrayList<Card> newCards;

    private boolean firstNextRound;

    private int stackCardsCount;

    private int enemyCardsCount;

    public PlayerStopDefenseEvent(String gameId, String destinationPlayer, List<Card> cards, boolean firstNextRound, int stackCardsCount, int enemyCardsCount) {
        super(gameId, PLAYER_STOP_ATTACK);
        this.destinationPlayer = destinationPlayer;
        this.newCards = new ArrayList<>(cards);
        this.firstNextRound = firstNextRound;
        this.stackCardsCount = stackCardsCount;
        this.enemyCardsCount = enemyCardsCount;
    }

}
