package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText().length() < 1 || message.getMessageText().length() > 255)
            return null;
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return null;
    }

    public int deleteMessage(int id) {
        return 0;
    }

    public int updateMessage(int id) {
        return 0;
    }

    public List<Message> getAllMessagesByAccount(Account account) {
        return null;
    }

}
