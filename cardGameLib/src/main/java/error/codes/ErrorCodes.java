package error.codes;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ErrorCodes {

    public static final String FATAL_ERROR = "internalServerError";
    public static final String UNAUTHORIZED_REQ = "unauthorized";

    public static final String LOGIN_TOO_SHORT = "registration.validation.login.tooShort";
    public static final String LOGIN_INVALID_FORMAT = "registration.validation.login.invalidFormat";
    public static final String LOGIN_ALREADY_USED = "registration.validation.login.alreadyUsed";
    public static final String PASSWORD_NOT_MATCH = "password.validation.notMatch";
    public static final String PASSWORD_TOO_SHORT = "password.validation.tooShort";
    public static final String BAD_CREDENTIALS = "auth.badCredentials";

    public static final String GAME_NOT_FOUND = "game.notFound";
    public static final String GAME_IS_CLOSED = "game.organization.closed";
    public static final String GAME_IS_FULL = "game.organization.full";

    public static final String PLAYER_NOT_FOUND = "player.notFound";

}
