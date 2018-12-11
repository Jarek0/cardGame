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
public class PlayerStopDefenseEvent extends GameProgressEvent {

    private String destinationPlayer;

    private ArrayList<Card> newCards;

    private boolean firstNextRound;

    public PlayerStopDefenseEvent(String gameId, String destinationPlayer, List<Card> cards, boolean firstNextRound) {
        super(gameId, PLAYER_STOP_ATTACK);
        this.destinationPlayer = destinationPlayer;
        this.newCards = new ArrayList<>(cards);
        this.firstNextRound = firstNextRound;
    }

}
