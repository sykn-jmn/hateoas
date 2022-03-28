package com.jeman.hateoas.message;

import com.jeman.hateoas.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByFrom_IdAndTo_id(Long from, Long to, Pageable pageable);

    @Query("Select distinct to from Message as msg where msg.from.id = ?1")
    List<User> findFriendsWithMessages(Long from);
}
