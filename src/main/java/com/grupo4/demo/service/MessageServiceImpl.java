package com.grupo4.demo.service;

import com.grupo4.demo.model.Message;
import com.grupo4.demo.model.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageDAO messageDAO;

    @Autowired
    public MessageServiceImpl(MessageDAO messageDAO) {
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
