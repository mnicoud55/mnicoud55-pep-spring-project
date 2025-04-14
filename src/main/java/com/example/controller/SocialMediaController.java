package com.example.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/*
 * Controller class for all endpoints related to either accounts or messages.
 * See further below for exception handling.
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
        if (newAccount.getUsername() == null || newAccount.getUsername().length() < 1 || newAccount.getPassword() == null || newAccount.getPassword().length() < 4)
            throw new IllegalArgumentException("Invalid username or password.");
        return ResponseEntity.ok(accountService.createAccount(newAccount));
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account verify) {
        return ResponseEntity.ok(accountService.verifyAccount(verify));
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) {
        if (accountService.getAccountById(newMessage.getPostedBy()).isEmpty())
            throw new IllegalArgumentException("No account with ID " + newMessage.getPostedBy() + " found.");
        return ResponseEntity.ok(messageService.createMessage(newMessage));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}") 
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Optional<Message> message = messageService.getMessage(messageId);
        if (message.isEmpty())
            throw new NoSuchElementException("No message with ID " + messageId + " found.");
        return ResponseEntity.ok(messageService.getMessage(messageId).get());
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok(1);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageText(@RequestBody Message newMessage, @PathVariable int messageId) {
        messageService.updateMessage(newMessage, messageId);
        return ResponseEntity.ok(1);    
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable int accountId) {
        return ResponseEntity.ok(messageService.getAllMessagesByAccount(accountId));
    }

    /* 
     * Exceptions handled below.
     * View the AccountService and MessageService classes to determine when specific exceptions are thrown.
    */

    // Handles duplicate usernames with response status 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(DataIntegrityViolationException ex) {
        return ex.getMessage();
    }

    // Handles invalid login attempt with response status 401
    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleInvalidLogin(SecurityException ex) {
        return ex.getMessage();
    }

    // Handles cases of idempotency with expected response status 200 with no body
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.OK)
    public void handleIdempotentNoElementFound(NoSuchElementException ex) {}

    // Handles all other potential exceptions with response status 400
    // NullPointerException not expected but included for safety
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleGeneralClientError(RuntimeException ex) {
        return ex.getMessage();
    }
}
