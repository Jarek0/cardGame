package event.game.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import event.CardGameEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GameClosedEvent.class, name = "GameClosedEvent"),
        @JsonSubTypes.Type(value = GameStartedEvent.class, name = "GameStartedEvent")
})
public abstract class GameOrganizationEvent extends CardGameEvent {

    private String gameId;

    private GameOrganizationEventType eventType;

    GameOrganizationEvent(String gameId, GameOrganizationEventType eventType) {
        super("/queue/game/" + gameId);
        this.gameId = gameId;
        this.eventType = eventType;
    }
}
