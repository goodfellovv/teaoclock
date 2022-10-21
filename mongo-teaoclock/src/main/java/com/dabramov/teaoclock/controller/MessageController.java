package com.dabramov.teaoclock.controller;

import com.dabramov.teaoclock.entity.Addressee;
import com.dabramov.teaoclock.entity.Message;
import com.dabramov.teaoclock.repository.AddresseeRepository;
import com.dabramov.teaoclock.repository.MessageRepository;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MessageController {
    private MessageRepository messageRepository;
    private AddresseeRepository addresseeRepository;

    @GetMapping("/messages")
    public List<Message> getMessages(@RequestParam(name = "ids", required = false) List<String> ids) {
        if (ids == null) {
            return messageRepository.findAll();
        }
        return (List<Message>) messageRepository.findAllById(ids);
    }

    @GetMapping("/messages/{id}")
    public Message getMessage(@PathVariable String id) {
        return messageRepository.findById(id).orElse(null);
    }

    @PostMapping("/messages")
    public Message saveMessage(@RequestBody Message message) {
        if(Objects.nonNull(message.getAddressees())){
            List<String> addresseeIds = message.getAddressees().stream().map(Addressee::getId).collect(Collectors.toList());
            Iterable<Addressee> addressees = addresseeRepository.findAllById(addresseeIds);
            message.setAddressees((List<Addressee>) addressees);
        }
        return messageRepository.save(message);
    }

    @DeleteMapping("/messages/{id}")
    public void deleteMessage(@PathVariable String id) {
        messageRepository.deleteById(id);
    }
}
