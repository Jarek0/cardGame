package event;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static event.GameOrganizationEventType.PLAYER_JOINED;

@Getter
@EqualsAndHashCode(callSuper = true)
public class PlayerJoinGameEvent extends GameOrganizationEvent {

    private final String founderLogin;

    private final String playerLogin;

    public PlayerJoinGameEvent(String founderLogin, String playerLogin, String gameId) {
        super(gameId, PLAYER_JOINED);
        this.founderLogin = founderLogin;
        this.playerLogin = playerLogin;
    }
}
