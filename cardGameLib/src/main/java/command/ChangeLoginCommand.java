package command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static error.codes.ErrorCodes.LOGIN_INVALID_FORMAT;
import static error.codes.ErrorCodes.LOGIN_TOO_SHORT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeLoginCommand implements CardGameCommand {

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = LOGIN_INVALID_FORMAT)
    @Size(min = 5, message = LOGIN_TOO_SHORT)
    private String login;

}
