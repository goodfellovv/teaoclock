package com.dabramov.teaoclock.mailsender;

import com.dabramov.teaoclock.service.dayoff.DayOffService;
import com.dabramov.teaoclock.service.mail.EmailService;
import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Profile("quartz")
@AllArgsConstructor
public class QuartzEmailJob implements Job {
    private final EmailService emailService;
    private final DayOffService dayOffService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        if(!dayOffService.isDayOff(LocalDate.now().getDayOfYear())) {
            emailService.sendEmailToAll();
        }
    }
}
