package com.example.controller;

import javax.websocket.server.PathParam;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account newAccount) {
        /* PUT THIS ELSEWHERE?????
        if (newAccount.getUsername() == null || newAccount.getUsername().length() < 1 || newAccount.getPassword() == null || newAccount.getPassword().length() < 1)
            return ResponseEntity.status(400).body("Client error");
        for (int i = 0; i < accountsList.size(); i++)
            if (accountsList.get(i).getUsername() == newAccount.getUsername())
                return ResponseEntity.status(409).body("Conflict");

        accountsList.add(newAccount);*/
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account) {
        return ResponseEntity.ok(account);
    }

    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody Message newMessage) {
        return ResponseEntity.status(400).body("Client error");
        //ResponseEntity.ok(newMessage);
    }

    @GetMapping("/messages/{messageId}") 
    public ResponseEntity getMessageById(@PathVariable int messageId) {
        return ResponseEntity.status(400).body("Client error");
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessageById(@PathVariable int messageId) {
        return ResponseEntity.ok("");
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMessageText(@PathVariable int messageId) {
        return ResponseEntity.status(400).body("Client error");
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getAllMessagesByUser(@PathVariable int accountId) {
        return ResponseEntity.ok("");
    }
    
}
