package pl.pollub.edu.cardGame.game.domain.card;

import lombok.RequiredArgsConstructor;
import pl.pollub.edu.cardGame.game.domain.Player;

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
