package command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static error.codes.ErrorCodes.PASSWORD_TOO_SHORT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordCommand  implements CardGameCommand {

    @NotEmpty
    @Size(min = 5, message = PASSWORD_TOO_SHORT)
    private String oldPassword;

    @NotEmpty
    @Size(min = 5, message = PASSWORD_TOO_SHORT)
    private String newPassword;

    private String newPasswordConfirmation;
}
