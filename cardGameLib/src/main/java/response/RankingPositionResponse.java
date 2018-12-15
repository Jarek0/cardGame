package response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingPositionResponse {

    private String id;

    private int index;

    private String playerLogin;

    private int points;


    @Override
    public String toString() {
        return index + ". " + playerLogin + ": " + points;
    }
}
