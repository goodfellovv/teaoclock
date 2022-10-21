package com.dabramov.teaoclock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "MESSAGE")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTENT", length = 1000)
    private String content;

    @Column(name = "SUBJECT")
    private String subject;

    @OneToMany(mappedBy = "message")
    @JsonIgnoreProperties("message")
    private List<Addressee> addressees;
}
