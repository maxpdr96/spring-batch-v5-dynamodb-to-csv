package com.hidarisoft.springbatchv5dynamodbtocsv.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    @Bean
    public JobDetail quartzJobDetail() {
        return JobBuilder.newJob(BatchScheduleJob.class).storeDurably().build();
    }

    @Bean
    public Trigger jobTrigger() {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(15)
                .repeatForever();

        return TriggerBuilder
                .newTrigger()
                .forJob(quartzJobDetail())
                .withSchedule(simpleScheduleBuilder)
                .build();
    }
}
