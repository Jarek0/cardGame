package event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GameClosedEvent.class, name = "GameClosedEvent"),
        @JsonSubTypes.Type(value = GameStartedEvent.class, name = "GameStartedEvent")
})
abstract class GameOrganizationEvent extends CardGameEvent {

    private GameOrganizationEventType eventType;

    private String gameId;

    GameOrganizationEvent(String gameId, GameOrganizationEventType eventType) {
        super("/queue/game/" + gameId);
        this.eventType = eventType;
        this.gameId = gameId;
    }
}
