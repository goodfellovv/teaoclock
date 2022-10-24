package com.dabramov.teaoclock.mailsender;

import lombok.AllArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@Profile("quartz")
@AllArgsConstructor
public class QuartzEmailSender {
    private final Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.start();
    }

    @PreDestroy
    public void destroy() throws SchedulerException {
        scheduler.shutdown();
    }
}
