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

    public static Card of(CardColor color, CardValue strange) {
        return new Card(color, strange);
    }

    public boolean isSameColor(Card card) {
        return this.getColor().equals(card.getColor());
    }

    public boolean isSameStrange(Card card) {
        return this.getStrange() == card.getStrange();
    }

    public int getStrange() {
        return value.getValue();
    }
}
