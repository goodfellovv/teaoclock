package com.dabramov.teaoclock.configuration;

import com.dabramov.teaoclock.mailsender.QuartzEmailJob;
import lombok.AllArgsConstructor;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("quartz")
public class QuartzConfiguration {
    @Value("${email.cron}")
    private String emailCron;
    private final String GROUP_NAME = "QuartzEmailGroup";
    private final String TRIGGER_NAME = "QuartzEmailTrigger";
    private final String JOB_NAME = "QuartzEmailJob";

    @Bean
    @Primary
    public Scheduler getQuartzEmailScheduler() throws SchedulerException {
        boolean validExpression = CronExpression.isValidExpression(emailCron);
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(getQuartzEmailJob(), getQuartzEmailTrigger());
        return scheduler;
    }

    public Trigger getQuartzEmailTrigger() {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(TRIGGER_NAME, GROUP_NAME)
                .withSchedule(CronScheduleBuilder.cronSchedule(emailCron))
                .build();
    }

    public JobDetail getQuartzEmailJob() {
        JobKey quartzEmailJob = new JobKey(JOB_NAME, GROUP_NAME);
        return JobBuilder.newJob(QuartzEmailJob.class)
                .withIdentity(quartzEmailJob)
                .build();
    }

}
