package com.scheduler.shedLock.config;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.scheduler.shedLock.config.expression.DynamicCronExpression;
import com.scheduler.shedLock.service.ScheduleJob;

@Service
public class UpdateScheduler {

    @Autowired
    private ScheduleJob scheduleJob;

    @Autowired
    private DynamicCronExpression expression;

    @Autowired
    private TaskScheduler scheduler;

    private String cron = "*/5 * * * * ?";

    private ScheduledFuture<?> future;

    
    @PostConstruct
    public void start() { //여기서 scheduleJob만 바꿔주면 된다.

        ScheduledFuture<?> future = this.scheduler.schedule(
            
        () -> scheduleJob.scheduledTask(),

                new CronTrigger(this.cron));

        this.future = future;

    }



    public void changeCron() {
        
        if (future != null) future.cancel(true);

        this.future = null;

        this.cron = expression.getCron();

        this.start();

    }

}