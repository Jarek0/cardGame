package event.game.progress;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static event.game.progress.GameProgressEventType.GAME_FINISHED;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GameFinishedEvent extends GameProgressEvent {

    private String winnerLogin;

    private String destinationPlayer;

    public GameFinishedEvent(String gameId, String winnerLogin, String destinationPlayer) {
        super(gameId, GAME_FINISHED);
        this.winnerLogin = winnerLogin;
        this.destinationPlayer = destinationPlayer;
    }

}
