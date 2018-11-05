package pl.pollub.edu.cardGame.game.organization.exceptions;

import pl.pollub.edu.cardGame.common.exception.ExceptionWithCode;
import pl.pollub.edu.cardGame.common.exception.impl.BusinessValidationException;

public class PlayerAlreadyInGameException extends BusinessValidationException implements ExceptionWithCode {

    private static final String PLAYER_ALREADY_IN_GAME = "game.organization.player.alreadyInGame";

    public PlayerAlreadyInGameException(String login) {
        super(PLAYER_ALREADY_IN_GAME, "Player with: " + login + " is already in game");
    }

}
