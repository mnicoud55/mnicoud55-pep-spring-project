package com.example.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

/*
 * Service class for the Message entity.
 */
@Service
@Transactional
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) throws IllegalArgumentException {
        if (message == null || message.getMessageText().length() < 1 || message.getMessageText().length() > 255)
            throw new IllegalArgumentException("Invalid message length.");
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessage(int id) {
        return messageRepository.findById(id);
    }

    public void deleteMessage(int id) throws NoSuchElementException {
        if (getMessage(id).isEmpty())
            throw new NoSuchElementException("No message with ID " + id + " found.");
        messageRepository.deleteById(id);
    }

    public void updateMessage(Message newMessage, int id) throws RuntimeException {
        if (newMessage == null || getMessage(id).isEmpty() || newMessage.getMessageText().length() < 1 || newMessage.getMessageText().length() > 255)
            throw new IllegalArgumentException("Invalid message.");
        messageRepository.save(newMessage);
    }

    public List<Message> getAllMessagesByAccount(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

}
