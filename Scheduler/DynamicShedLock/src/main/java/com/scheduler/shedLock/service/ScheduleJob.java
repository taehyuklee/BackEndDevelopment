package com.scheduler.shedLock.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scheduler.shedLock.config.expression.DynamicCronExpression;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Component
public class ScheduleJob {

    @Autowired
    private DynamicCronExpression expression;


    @Scheduled(cron = "*/5 * * * * ?")
    @SchedulerLock(name="SchedulerLock", lockAtMostFor = "PT1S", lockAtLeastFor = "PT1S")
    public void scheduledTask() {

        System.out.println("First Schedule");
        System.out.println(LocalDateTime.now());

    }

    // @Scheduled(cron = "*/1 * * * * ?")
    // @SchedulerLock(name="SchedulerLock", lockAtMostFor = "PT1S", lockAtLeastFor = "PT1S")
    // public void scheduledTask2() {

    //     System.out.println("Second Schedule");
    //     System.out.println(LocalDateTime.now());

    // }
}