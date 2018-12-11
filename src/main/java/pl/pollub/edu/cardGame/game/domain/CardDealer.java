package pl.pollub.edu.cardGame.game.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CardDealer {

    private final CardStack cardStack;

    public CardStack dealCards(List<Player> players) {
        cardStack.shuffleCards();
        for(Player player : players) {
            player.addCards(cardStack.getCards(5));
        }

        return cardStack;
    }
}
