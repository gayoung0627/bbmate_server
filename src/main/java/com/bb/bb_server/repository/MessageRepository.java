package com.bb.bb_server.repository;

import com.bb.bb_server.domain.Message;
import com.bb.bb_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiver(User user);
    List<Message> findAllBySender(User user);
}
