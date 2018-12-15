package pl.pollub.edu.cardGame.game.ranking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Secured("ROLE_PLAYER")
@RequiredArgsConstructor
public class RankingPositionController {

    private final RankingPositionService service;

    @GetMapping("/api/ranking")
    public ResponseEntity getRankingPositions(Pageable pageable, RankingFilter filter) {
        return ResponseEntity.ok(service.findByPlayerLogin(pageable, filter));
    }
}
