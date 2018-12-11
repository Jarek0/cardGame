package event.game.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import event.CardGameEvent;
import event.game.GameEvent;
import lombok.AllArgsConstructor;
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
public abstract class GameOrganizationEvent extends GameEvent {

    GameOrganizationEvent(String gameId, GameOrganizationEventType eventType) {
        super(gameId, eventType);
    }
}
