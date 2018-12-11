package pl.pollub.edu.cardGame.game.repository;

import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;
import pl.pollub.edu.cardGame.game.domain.Game;
import pl.pollub.edu.cardGame.game.domain.QGame;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends MongoRepository<Game, ObjectId>, QuerydslPredicateExecutor<Game>, QuerydslBinderCustomizer<QGame> {

    @Query(value = "{ 'status' : 'OPEN', '_id' : ?0 }")
    Optional<Game> findOpenById(ObjectId gameId);

    @Query(value = "{ 'status' : 'STARTED', '_id' : ?0 }")
    Optional<Game> findStartedById(ObjectId gameId);

    @Query(value = "{ 'status' : 'OPEN' }")
    Page<Game> findOpenGames(Pageable pageable);

    @Query(value = "{ 'status' : { $ne : 'ENDED' }, 'players' : { $elemMatch : { 'login' : ?0 } } }")
    List<Game> findNotEndedByPlayerLogin(String playerLogin);

    @Query(value = "{ 'status' : { $ne : 'ENDED' } }")
    List<Game> findNotEnded();

    @Override
    default void customize(QuerydslBindings bindings, QGame root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) SimpleExpression::eq);
    }
}
