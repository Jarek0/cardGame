package pl.pollub.edu.cardGame.game.ranking;

import com.querydsl.core.types.dsl.BooleanExpression;
import event.game.progress.GameFinishedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.edu.cardGame.authentication.domain.Authentication;
import response.PageResponse;
import response.RankingPositionResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RankingPositionService {

    private final RankingPositionRepository repository;

    @Transactional
    public void addPointsForPlayer(GameFinishedEvent event) {
        String winnerLogin = event.getWinnerLogin();
        RankingPosition rankingPosition = repository.findByPlayerLogin(winnerLogin)
                .orElseGet(() -> new RankingPosition(winnerLogin));
        rankingPosition.addPoints(event.getPoints());
        repository.save(rankingPosition);
    }

    @Transactional
    public void changePlayerLoginForPositions(Authentication playerToChangeLogin, String newLogin) {
        List<RankingPosition> rankingPositions = repository.findAllByPlayerLogin(playerToChangeLogin.getUsername());
        for(RankingPosition rankingPosition : rankingPositions) {
            rankingPosition.setPlayerLogin(newLogin);
        }
        repository.saveAll(rankingPositions);
    }

    @Transactional(readOnly = true)
    public PageResponse<RankingPositionResponse> findByPlayerLogin(Pageable pageable, RankingFilter filter) {
        QRankingPosition rankingPosition = QRankingPosition.rankingPosition;
        BooleanExpression predicate = rankingPosition.playerLogin.containsIgnoreCase(filter.getPlayerLogin());
        Page<RankingPosition> rankingPage = repository.findAll(predicate, pageable);
        List<RankingPositionResponse> pageResContent = rankingPage.getContent().isEmpty() ?
                Collections.emptyList() :
                IntStream.range(0, rankingPage.getContent().size())
                .mapToObj(i -> rankingPage.getContent().get(i).toResponse(i+1))
                .collect(Collectors.toList());
        return new PageResponse<>(
                rankingPage.getTotalPages(),
                rankingPage.getNumber(),
                pageResContent
        );
    }
}
