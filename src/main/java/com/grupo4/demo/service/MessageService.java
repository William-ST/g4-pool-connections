package com.grupo4.demo.service;

import com.grupo4.demo.model.Message;

import java.util.List;

public interface MessageService {
    List<Message> findAll();
    Message save(Message message);
}
