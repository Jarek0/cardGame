package event.game.organization;

import lombok.*;
import model.Card;

import java.util.ArrayList;
import java.util.List;

import static event.game.organization.GameOrganizationEventType.GAME_STARTED;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GameStartedEvent extends GameOrganizationEvent {

    private String destinationPlayer;

    private String enemyLogin;

    private ArrayList<Card> cards;

    private Card trump;

    private boolean startFirst;

    public GameStartedEvent(String destinationPlayer, String enemyLogin, String gameId, List<Card> cards, Card trump, boolean startFirst) {
        super(gameId, GAME_STARTED);
        this.destinationPlayer = destinationPlayer;
        this.enemyLogin = enemyLogin;
        this.cards = new ArrayList<>(cards);
        this.trump = trump;
        this.startFirst = startFirst;
    }

}
