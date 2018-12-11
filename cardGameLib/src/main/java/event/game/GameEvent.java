package event.game;

import event.CardGameEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
abstract public class GameEvent extends CardGameEvent {

    private GameEventType eventType;

    private String gameId;

    protected GameEvent(String gameId, GameEventType eventType) {
        super("/queue/game/" + gameId);
        this.eventType = eventType;
        this.gameId = gameId;
    }

}
