package com.dabramov.teaoclock.controller;

import com.dabramov.teaoclock.entity.Message;
import com.dabramov.teaoclock.repository.MessageRepository;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MessageController {
    private MessageRepository messageRepository;

    @GetMapping("/messages/{id}")
    public Message getMessage(@PathVariable Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    @GetMapping("/messages")
    public List<Message> getMessages(@RequestParam(name = "ids", required = false) List<Long> ids) {
        if (ids == null) {
            return messageRepository.findAll();
        }
        return messageRepository.findAllById(ids);
    }

    @PostMapping("/messages")
    public Message saveMessage(@RequestBody Message message) {
        return messageRepository.save(message);
    }

    @DeleteMapping("/messages/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageRepository.deleteById(id);
    }
}
