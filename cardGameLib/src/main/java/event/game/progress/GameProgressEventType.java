package event.game.progress;

import event.game.GameEventType;

public enum GameProgressEventType implements GameEventType {
    PLAYER_ATTACKED, PLAYER_DEFENDED, GAME_FINISHED, PLAYER_STOP_ATTACK, PLAYER_STOP_DEFENSE;
}
