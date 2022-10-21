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

@Api
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AddresseeController {
    private AddresseeRepository addresseeRepository;
    private MessageRepository messageRepository;

    @GetMapping("/addressees/{id}")
    public Addressee getAddressee(@PathVariable Long id) {
        return addresseeRepository.findById(id).orElse(null);
    }

    @GetMapping("/addressees")
    public List<Addressee> getAddressees(@RequestParam(name = "ids", required = false) List<Long> ids) {
        if (ids == null) {
            return addresseeRepository.findAll();
        }
        return addresseeRepository.findAllById(ids);
    }

    @PostMapping("/addressees")
    public Addressee saveAddressee(@RequestBody Addressee addressee) {
        if(Objects.nonNull(addressee.getMessage())){
            Message message = messageRepository.findById(addressee.getMessage().getId()).orElse(null);
            addressee.setMessage(message);
        }
        return addresseeRepository.save(addressee);
    }

    @DeleteMapping("/addressees/{id}")
    public void deleteAddressee(@PathVariable Long id) {
        addresseeRepository.deleteById(id);
    }
}
