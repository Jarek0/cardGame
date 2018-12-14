package pl.pollub.edu.cardGame.game.domain;

import model.Card;
import model.CardColor;
import model.CardValue;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class BattleGround {

    private List<Card> cards = new LinkedList<>();

    boolean canCardAttack(Card card) {
        return cards.isEmpty() || cards.stream().anyMatch(c -> c.isSameValue(card));
    }

    boolean canCardDefense(Card card, CardColor trumpColor) {
        if(cards.isEmpty()) throw new IllegalStateException("Attack card not exists");
        return card.isStrangerThan(getLastCard(), trumpColor);
    }

    void putCard(Card card) {
        cards.add(card);
    }

    List<Card> finishBattle() {
        List<Card> finishedBattleCards = cards;
        cards = new LinkedList<>();
        return finishedBattleCards;
    }

    boolean isEmpty() { return cards.isEmpty(); }

    Set<CardValue> valuesOnBattleGround() {
        return cards.stream().map(Card::getValue).collect(Collectors.toSet());
    }

    private Card getLastCard() {
        return cards.get(cards.size() - 1);
    }

}
