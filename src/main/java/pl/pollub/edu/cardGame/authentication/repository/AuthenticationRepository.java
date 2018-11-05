package pl.pollub.edu.cardGame.authentication.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.pollub.edu.cardGame.authentication.domain.Authentication;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends MongoRepository<Authentication, ObjectId> {

    Optional<Authentication> findByLogin(String login);

}
