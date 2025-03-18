package com.grupo4.demo.repository;

import com.grupo4.demo.model.Message;
import com.grupo4.demo.model.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository{

    private final MessageDAO messageDAO;

    @Autowired
    public MessageRepositoryImpl(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public List<Message> findAll() {
        return messageDAO.findMessage();
    }

    @Override
    public Message save(Message message) {
        return messageDAO.create(message);
    }
}
