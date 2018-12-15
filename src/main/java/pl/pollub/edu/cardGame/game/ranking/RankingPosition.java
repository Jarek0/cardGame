package pl.pollub.edu.cardGame.game.ranking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import response.RankingPositionResponse;

@Data
@Document(collection = "RankingPosition")
@AllArgsConstructor
public class RankingPosition {

    @Id
    private ObjectId id;

    private String playerLogin;

    private int points;

    RankingPosition(String playerLogin) {
        this.playerLogin = playerLogin;
        this.points = 0;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public RankingPositionResponse toResponse(int index) {
        return new RankingPositionResponse(this.id.toString(), index, this.playerLogin, this.points);
    }
}
