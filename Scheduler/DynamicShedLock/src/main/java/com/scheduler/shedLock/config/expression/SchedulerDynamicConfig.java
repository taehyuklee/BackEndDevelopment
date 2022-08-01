package com.scheduler.shedLock.config.expression;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import com.scheduler.shedLock.config.expression.DynamicCronExpression;
import com.scheduler.shedLock.service.ScheduleJob;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SchedulerDynamicConfig implements SchedulingConfigurer{

    @Autowired
    private ScheduleJob scheduleJob;

    @Autowired
    private DynamicCronExpression expression;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        List<CronTask> taskList = taskRegistrar.getCronTaskList();

        System.out.println(taskList.toString());
        System.out.println("/////////////////////////");
  
        taskRegistrar.addTriggerTask(

                    new Runnable(){
                        @Override
                        public void run(){
                            scheduleJob.scheduledTask();
                        }
                    }, 
                    
                    triggerContext -> {

                        log.info("changed");
                        log.info(expression.getCron());
                        return new CronTrigger((expression.getCron())).nextExecutionTime(triggerContext);
                        
        }
        );
    }
}
