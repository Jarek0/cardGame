package event.authentication;

import event.CardGameEvent;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LogoutUserEvent extends CardGameEvent {

    private String user;

    public LogoutUserEvent(String user) {
        super("/queue/auth");
        this.user = user;
    }

}
