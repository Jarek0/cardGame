package event.game.progress;

import model.Card;

import java.util.List;

public interface NextRoundEvent {

    int getStackCardsCount();

    int getEnemyCardsCount();

    boolean isFirstNextRound();

    List<Card> getNewCards();

}
