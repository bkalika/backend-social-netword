package com.bkalika.socialnetwork.repositories;

import com.bkalika.socialnetwork.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author @bkalika
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findByToken(String token);

    @Query(value = "select u from User u where u.firstName like :term or u.lastName like :term or u.login like :term")
    List<User> search(@Param("term") String term);
}
