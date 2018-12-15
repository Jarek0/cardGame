package command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static error.codes.ErrorCodes.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCommand  implements CardGameCommand {

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = LOGIN_INVALID_FORMAT)
    @Size(min = 5, max = 20, message = LOGIN_TOO_SHORT)
    private String login;

    @NotEmpty
    @Size(min = 5, max = 20, message = PASSWORD_TOO_SHORT)
    private String password;

    private String passwordConfirmation;
}
