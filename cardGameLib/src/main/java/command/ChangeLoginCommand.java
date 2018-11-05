package command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static error.codes.ErrorCodes.LOGIN_TOO_SHORT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeLoginCommand implements CardGameCommand {

    @NotEmpty
    @Size(min = 5, message = LOGIN_TOO_SHORT)
    private String login;

}
