package event;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
abstract class GameOrganizationEvent extends CardGameEvent {

    private final GameOrganizationEventType eventType;

    private final String gameId;

    GameOrganizationEvent(String gameId, GameOrganizationEventType eventType) {
        super("/game/" + gameId + "/organization");
        this.eventType = eventType;
        this.gameId = gameId;
    }
}
