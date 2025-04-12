package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText().length() < 1 || message.getMessageText().length() > 255)
            return null;
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessage(int id) {
        return messageRepository.findById(id);
    }

    public void deleteMessage(int id) throws RuntimeException {
        if (getMessage(id).isEmpty())
            throw new IllegalArgumentException();
        messageRepository.deleteById(id);
    }

    public void updateMessage(Message newMessage, int id) throws RuntimeException {
        if (newMessage == null || getMessage(id).isEmpty() || newMessage.getMessageText().length() < 1 || newMessage.getMessageText().length() > 255)
            throw new IllegalArgumentException();
        messageRepository.save(newMessage);
    }

    public List<Message> getAllMessagesByAccount(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

}
