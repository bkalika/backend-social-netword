package com.bkalika.socialnetwork.repositories;

import com.bkalika.socialnetwork.entities.MongoMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author @bkalika
 */
public interface MongoMessageRepository extends PagingAndSortingRepository<MongoMessage, String> {
    List<MongoMessage> findAllByUserIdIn(List<String> ids, Pageable pageable);
}
