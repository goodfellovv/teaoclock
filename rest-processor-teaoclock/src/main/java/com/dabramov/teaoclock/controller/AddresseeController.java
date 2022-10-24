package com.dabramov.teaoclock.controller;

import com.dabramov.teaoclock.dto.AddresseeDto;
import com.dabramov.teaoclock.server.DataServer;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@CrossOrigin
@RestController
@RequestMapping("/addressees")
@AllArgsConstructor
public class AddresseeController {
    private final DataServer dataServer;

    @GetMapping(value = "/getAll")
    public List<AddresseeDto> getAllAddresses() {
        return dataServer.getAllAddressees();
    }

    @PostMapping("/save")
    public AddresseeDto saveAddressee(@RequestBody AddresseeDto addresseeDto) {
        return dataServer.saveAddressee(addresseeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAddressee(@PathVariable String id) {
        dataServer.deleteAddressee(id);
    }
}
