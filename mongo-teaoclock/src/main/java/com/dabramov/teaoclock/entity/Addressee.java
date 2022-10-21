package com.dabramov.teaoclock.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class Addressee implements Serializable {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    @DBRef
    private Message message;
}
