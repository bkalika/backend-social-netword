package com.bkalika.socialnetwork.repositories;

import com.bkalika.socialnetwork.entities.MongoUser;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author @bkalika
 */
public interface MongoUserRepository extends PagingAndSortingRepository<MongoUser, String> {
    Optional<MongoUser> findByLogin(String login);
    @Query("{$or: [{firstName: /?0/}, {lastName: /?0/}, {login: /?0/}]}")
    List<MongoUser> searchUsers(String term);
}
