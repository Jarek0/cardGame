package event;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

import static event.GameOrganizationEventType.GAME_CLOSED;

@Getter
@EqualsAndHashCode(callSuper = true)
public class GameClosedEvent extends GameOrganizationEvent {

    private final List<String> players;

    public GameClosedEvent(List<String> players, String gameId) {
        super(gameId, GAME_CLOSED);
        this.players = players;
    }
}
