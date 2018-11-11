package event;

import lombok.*;
import model.Card;

import java.util.ArrayList;
import java.util.List;

import static event.GameOrganizationEventType.GAME_STARTED;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GameStartedEvent extends GameOrganizationEvent {

    private String playerLogin;

    private ArrayList<Card> cards;

    private Card trump;

    public GameStartedEvent(String playerLogin, String gameId, List<Card> cards, Card trump) {
        super(gameId, GAME_STARTED);
        this.playerLogin = playerLogin;
        this.cards = new ArrayList<>(cards);
        this.trump = trump;
    }

}
