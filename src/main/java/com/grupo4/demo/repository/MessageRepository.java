package com.grupo4.demo.repository;

import com.grupo4.demo.model.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> findAll();
    Message save(Message message);
}
