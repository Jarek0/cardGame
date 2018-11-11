package event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GameOrganizationEvent.class, name = "GameOrganizationEvent"),
        @JsonSubTypes.Type(value = LogoutUserEvent.class, name = "LogoutUserEvent")
})
public abstract class CardGameEvent implements Serializable {

    private String destination;

}
