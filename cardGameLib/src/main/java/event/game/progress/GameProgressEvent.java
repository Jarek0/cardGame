package event.game.progress;

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
        @JsonSubTypes.Type(value = PlayerAttackedEvent.class, name = "PlayerAttackedEvent"),
        @JsonSubTypes.Type(value = PlayerDefendedEvent.class, name = "PlayerDefendedEvent"),
        @JsonSubTypes.Type(value = PlayerStopAttackEvent.class, name = "PlayerStopAttackEvent"),
        @JsonSubTypes.Type(value = PlayerStopDefenseEvent.class, name = "PlayerStopDefenseEvent"),
        @JsonSubTypes.Type(value = GameFinishedEvent.class, name = "GameFinishedEvent")
})
public abstract class GameProgressEvent extends CardGameEvent {

    private String gameId;

    private GameProgressEventType eventType;

    GameProgressEvent(String gameId, GameProgressEventType eventType) {
        super("/queue/game/" + gameId);
        this.gameId = gameId;
        this.eventType = eventType;
    }
}
