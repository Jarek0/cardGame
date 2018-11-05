package pl.pollub.edu.cardGame.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface ExtendedMongoRepository<T, ID extends Serializable> extends MongoRepository<T, ID>, QuerydslPredicateExecutor<T> {

    Page<T> query(Query query, Pageable pageable);

}
