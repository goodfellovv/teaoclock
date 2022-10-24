package com.dabramov.teaoclock.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Document
public class Message implements Serializable {
    @Id
    private String id;
    private String content;
    private String subject;
    @DBRef
    private List<Addressee> addressees;
}
