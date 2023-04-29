package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Component
class ScheduleJob {

    @Scheduled(cron = "*/3 * * * * ?")
    @SchedulerLock(name="SchedulerLock", lockAtMostFor = "PT10S", lockAtLeastFor = "PT10S")
    public void scheduledTask() {

        System.out.println("First Schedule");
        System.out.println(LocalDateTime.now());

    }

    @Scheduled(cron = "*/2 * * * * ?")
    @SchedulerLock(name="SchedulerLock", lockAtMostFor = "PT10S", lockAtLeastFor = "PT10S")
    public void scheduledTask2() {

        System.out.println("Second Schedule");
        System.out.println(LocalDateTime.now());

    }
}