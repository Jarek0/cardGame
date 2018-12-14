package pl.pollub.edu.cardGame.game.domain;

import lombok.EqualsAndHashCode;
import model.Card;
import model.RoundKind;

import java.util.ArrayList;
import java.util.List;

import static model.RoundKind.*;

@EqualsAndHashCode(of = {"login"})
class Player {

    public static final int CARDS_IN_HAND_COUNT = 5;

    private String login;

    private RoundKind roundKind = PASSIVE;

    private List<Card> cards = new ArrayList<>();

    Player(String login) {
        this.login = login;
    }

    String getLogin() {
        return login;
    }

    void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    List<Card> getCards() {
        return cards;
    }

    boolean isAttacker() {
        return roundKind.equals(ATTACK);
    }

    boolean isDefender() {
        return roundKind.equals(DEFENSE);
    }

    boolean isPassive() {
        return roundKind.equals(PASSIVE);
    }

    boolean hasLogin(String login) {
        return this.getLogin().equals(login);
    }

    boolean hasLastCard() {
        return cards.size() == 1;
    }

    boolean hasCard(Card card) {
        return cards.contains(card);
    }

    void removeCard(Card card) {
        cards.remove(card);
    }

    void startAttack() {
        roundKind = ATTACK;
    }

    void startBePassive() {
        roundKind = PASSIVE;
    }

    void startDefense() {
        roundKind = DEFENSE;
    }

    int cardsCountToFullHand() {
        return CARDS_IN_HAND_COUNT - this.cardsCount();
    }

    int cardsCount() { return cards.size(); }
}
