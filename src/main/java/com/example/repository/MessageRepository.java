package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Integer> {
    // Query to find all messages posted by the provided Account id
    List<Message> findByPostedBy(int postedBy);
}
