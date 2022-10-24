package com.dabramov.teaoclock.service.mail;

import com.dabramov.teaoclock.server.DataServer;
import com.dabramov.teaoclock.dto.AddresseeDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final DataServer dataServer;
    private final JavaMailSender emailSender;

    @Override
    public void sendEmailToAll() {
        dataServer.getAllAddressees().forEach(addressee -> {
            SimpleMailMessage message = new SimpleMailMessage();
            if (Objects.isNull(addressee.getMessage())) {
                fillDefault(addressee, message);
            } else {
                fillMessage(addressee, message);
            }
            emailSender.send(message);
        });
    }

    private void fillDefault(AddresseeDto addressee, SimpleMailMessage message) {
        message.setText("Пора пить чай!");
        message.setTo(addressee.getEmail());
        message.setSubject("Чаепитие");
    }

    private void fillMessage(AddresseeDto addressee, SimpleMailMessage message) {
        message.setTo(addressee.getEmail());
        message.setSubject(addressee.getMessage().getSubject());
        message.setText(addressee.getMessage().getContent());
    }
}
