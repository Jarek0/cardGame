package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Card implements Serializable {

    private CardColor color;

    private CardValue value;

    public String toResourceName() {
        return "card" + value.getName() + color.getName();
    }

    public static Card of(CardColor color, CardValue value) {
        return new Card(color, value);
    }
}
