package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Integer> {
    
    @Query("FROM Message WHERE postedBy = ?1")
    List<Message> findByPostedBy(int postedBy);

}
