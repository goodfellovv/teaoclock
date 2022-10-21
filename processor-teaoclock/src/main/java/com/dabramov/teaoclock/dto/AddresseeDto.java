package com.dabramov.teaoclock.dto;

import lombok.Data;

@Data
public class AddresseeDto {
    private String id;
    private String email;
    private MessageDto message;
}
