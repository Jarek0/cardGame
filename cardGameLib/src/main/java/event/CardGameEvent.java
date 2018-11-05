package event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public abstract class CardGameEvent implements Serializable {

    private final  String destination;

}
