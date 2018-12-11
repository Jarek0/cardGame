package pl.pollub.edu.cardGame.game.domain;

import model.Card;
import model.CardColor;

import java.util.LinkedList;
import java.util.List;

class BattleGround {

    private static final int TRUMP_VALUE = 9;

    private List<Card> cards = new LinkedList<>();

    boolean canCardAttack(Card card) {
        return cards.isEmpty() || cards.stream().anyMatch(c -> c.isSameColor(card));
    }

    boolean canCardDefense(Card card, CardColor trumpColor) {
        if(cards.isEmpty()) throw new IllegalStateException("Attack card not exists");
        int cardValue = isCardTrump(card, trumpColor) ? card.getStrange() + TRUMP_VALUE : card.getStrange();
        return getLastCard().getStrange() < cardValue;
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

    private boolean isCardTrump(Card card, CardColor trumpColor) {
        return card.getColor().equals(trumpColor);
    }

    private Card getLastCard() {
        return cards.get(cards.size() - 1);
    }

}
