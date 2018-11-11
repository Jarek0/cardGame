package pl.pollub.edu.cardGame.game.domain;

import model.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String login;

    private List<Card> cards = new ArrayList<>();

    Player(String login) {
        this.login = login;
    }

    String getLogin() {
        return login;
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    List<Card> getCards() {
        return cards;
    }
}
