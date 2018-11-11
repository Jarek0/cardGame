package event;

import lombok.*;

import static event.GameOrganizationEventType.PLAYER_JOINED;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerJoinGameEvent extends GameOrganizationEvent {

    private String founderLogin;

    private String playerLogin;

    public PlayerJoinGameEvent(String founderLogin, String playerLogin, String gameId) {
        super(gameId, PLAYER_JOINED);
        this.founderLogin = founderLogin;
        this.playerLogin = playerLogin;
    }
}
