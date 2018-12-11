package event.game.organization;

import lombok.*;

import java.util.List;

import static event.game.organization.GameOrganizationEventType.GAME_CLOSED;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GameClosedEvent extends GameOrganizationEvent {

    private List<String> players;

    public GameClosedEvent(List<String> players, String gameId) {
        super(gameId, GAME_CLOSED);
        this.players = players;
    }
}
