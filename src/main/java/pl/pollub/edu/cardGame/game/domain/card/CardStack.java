package pl.pollub.edu.cardGame.game.domain.card;


import model.Card;
import model.CardColor;
import pl.pollub.edu.cardGame.game.play.StackIsEmptyException;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.CardColor.*;
import static model.CardValue.*;
import static model.Card.of;

public class CardStack {

    public Card getCard() {
        Card card = cards.stream().findFirst().orElseThrow(StackIsEmptyException::new);
        cards.remove(0);
        return card;
    }

    public List<Card> getCards(int cardsNumber) {
        if(this.cards.isEmpty()) throw new StackIsEmptyException();
        List<Card> cards = this.cards.stream().limit(cardsNumber).collect(Collectors.toCollection(LinkedList::new));
        this.cards.removeAll(cards);
        return cards;
    }

    public Card getTrumpCard() {
        return cards.get(cards.size() - 1);
    }

    public CardColor getTrump() {
        return getTrumpCard().getColor();
    }

    void shuffleCards() {
        Collections.shuffle(cards);
    }

    private List<Card> cards = Stream.of(
            of(C, _6), of(D, _6), of(H, _6), of(S, _6),
            of(C, _7), of(D, _7), of(H, _7), of(S, _7),
            of(C, _8), of(D, _8), of(H, _8), of(S, _8),
            of(C, _9), of(D, _9), of(H, _9), of(S, _9),
            of(C, _10), of(D, _10), of(H, _10), of(S, _10),
            of(C, J), of(D, J), of(H, J), of(S, J),
            of(C, Q), of(D, Q), of(H, Q), of(S, Q),
            of(C, K), of(D, K), of(H, K), of(S, K),
            of(C, A), of(D, A), of(H, A), of(S, A)
    ).collect(Collectors.toCollection(LinkedList::new));
}
