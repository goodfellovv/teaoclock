package com.dabramov.teaoclock.mailsender;

import com.dabramov.teaoclock.service.dayoff.DayOffService;
import com.dabramov.teaoclock.service.mail.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Profile("scheduled")
@AllArgsConstructor
public class ScheduledEmailSender {
    private final EmailService emailService;
    private final DayOffService dayOffService;

    @Scheduled(cron = "${email.cron}")
    public void init() {
        if (!dayOffService.isDayOff(LocalDate.now().getDayOfYear())) {
            emailService.sendEmailToAll();
        }
    }
}
