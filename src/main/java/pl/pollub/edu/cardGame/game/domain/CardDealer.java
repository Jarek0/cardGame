package pl.pollub.edu.cardGame.game.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CardDealer {

    private final CardsStack cardsStack;

    public CardsStack dealCards(List<Player> players) {
        cardsStack.shuffleCards();
        for(Player player : players) {
            player.addCards(cardsStack.getCards(5));
        }

        return cardsStack;
    }
}
