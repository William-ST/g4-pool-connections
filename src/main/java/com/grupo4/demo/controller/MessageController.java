package com.grupo4.demo.controller;

import com.grupo4.demo.model.Message;
import com.grupo4.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Message> createMessage(Message message) {
        Message newMessage = messageService.save(message);
        return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
    }

    /*
    // READ (all)
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.findAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    */

    // READ (all)
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.findAll();
        return ResponseEntity.ok(messages);
    }

}
