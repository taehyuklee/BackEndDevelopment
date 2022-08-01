package com.scheduler.shedLock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.shedLock.config.UpdateScheduler;
import com.scheduler.shedLock.config.expression.DynamicCronExpression;
import com.scheduler.shedLock.service.ScheduleJob;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/newCron")
@Slf4j
public class cronController {

    @Autowired
    private DynamicCronExpression expression;

    @Autowired
    private UpdateScheduler updateScheduler;

    @GetMapping("/alter")
    public String alter(@RequestParam("newCron") String newCron){

        log.info("cron is changed to : {}", newCron);
        this.expression.setCron(newCron);
        System.out.println(expression.getCron());

        updateScheduler.changeCron();
        return "Change of cron is success";
    }
    
}
