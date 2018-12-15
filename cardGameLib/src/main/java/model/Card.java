package model;

import command.CardGameCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Card implements Serializable, CardGameCommand{

    private static final int TRUMP_VALUE = 9;

    private CardColor color;

    private CardValue value;

    public String toResourceName() {
        return "card" + value.getName() + color.getName();
    }

    public static Card of(CardColor color, CardValue strange) {
        return new Card(color, strange);
    }

    public boolean isSameValue(Card card) {
        return value.getValue() == card.value.getValue();
    }

    public boolean isStrangerThan(Card card, CardColor trump) {
        return this.calculateStrange(trump) > card.calculateStrange(trump);
    }

    public int calculateStrange(CardColor trump) {
        return hasColor(trump) ? value.getValue() + TRUMP_VALUE : value.getValue();
    }

    private boolean hasColor(CardColor color) {
        return this.getColor().equals(color);
    }

}
