package pl.pollub.edu.cardGame.game.ranking;

import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingPositionRepository extends MongoRepository<RankingPosition, ObjectId>, QuerydslPredicateExecutor<RankingPosition>, QuerydslBinderCustomizer<QRankingPosition> {

    @Override
    default void customize(QuerydslBindings bindings, QRankingPosition root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) SimpleExpression::eq);
    }

    Optional<RankingPosition> findByPlayerLogin(String playerLogin);

    @Query()
    List<RankingPosition> findAllByPlayerLogin(String playerLogin);
}
