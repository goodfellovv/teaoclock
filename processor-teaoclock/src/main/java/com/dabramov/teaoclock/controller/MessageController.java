package com.dabramov.teaoclock.controller;

import com.dabramov.teaoclock.server.DataServer;
import com.dabramov.teaoclock.dto.MessageDto;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@CrossOrigin
@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {
    private final DataServer dataServer;

    @GetMapping(value = "/getAll")
    public List<MessageDto> getAllMessages() {
        return dataServer.getAllMessages();
    }

    @PostMapping("/save")
    public MessageDto saveMessage(@RequestBody MessageDto messageDto) {
        return dataServer.saveMessage(messageDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable String id) {
        dataServer.deleteMessage(id);
    }
}
