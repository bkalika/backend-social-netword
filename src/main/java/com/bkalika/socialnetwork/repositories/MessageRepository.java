package com.bkalika.socialnetwork.repositories;

import com.bkalika.socialnetwork.entities.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author @bkalika
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserId(Long id);

    @Query("SELECT msg FROM Message msg WHERE msg.user.id in :ids ORDER BY msg.createdDate DESC")
    List<Message> findCommunityMessages(@Param("ids") List<Long> ids, Pageable pageable);
}
