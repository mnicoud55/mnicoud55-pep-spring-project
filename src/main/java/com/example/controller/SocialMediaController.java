package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount) {
        if (newAccount.getUsername() == null || newAccount.getUsername().length() < 1 || newAccount.getPassword() == null || newAccount.getPassword().length() < 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Account account = accountService.createAccount(newAccount);
        if (account == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.ok(account);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account verify) {
        Account account = accountService.verifyAccount(verify);
        if (account == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(account);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) {
        if (accountService.getAccountById(newMessage.getPostedBy()).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Message createdMessage = messageService.createMessage(newMessage);
        if (createdMessage == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}") 
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Optional<Message> message = messageService.getMessage(messageId);
        if (message.isPresent())
            return ResponseEntity.ok(messageService.getMessage(messageId).get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        try {
            messageService.deleteMessage(messageId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.ok(1);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageText(@RequestBody Message newMessage, @PathVariable int messageId) {
        try {
            messageService.updateMessage(newMessage, messageId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(1);    
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable int accountId) {
        return ResponseEntity.ok(messageService.getAllMessagesByAccount(accountId));
    }
    
}
