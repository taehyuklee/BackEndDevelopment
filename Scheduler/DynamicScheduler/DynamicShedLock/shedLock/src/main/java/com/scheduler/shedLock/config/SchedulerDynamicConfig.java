// package com.scheduler.shedLock.config;

// import java.util.Date;
// import java.util.List;
// import java.util.Set;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.scheduling.Trigger;
// import org.springframework.scheduling.TriggerContext;
// import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.scheduling.annotation.SchedulingConfigurer;
// import org.springframework.scheduling.config.CronTask;
// import org.springframework.scheduling.config.ScheduledTask;
// import org.springframework.scheduling.config.ScheduledTaskRegistrar;
// import org.springframework.scheduling.support.CronTrigger;

// import com.scheduler.shedLock.config.expression.DynamicCronExpression;
// import com.scheduler.shedLock.service.ScheduleJob;

// import lombok.extern.slf4j.Slf4j;

// @Configuration
// @EnableScheduling
// @Slf4j
// public class SchedulerDynamicConfig implements SchedulingConfigurer{

//     @Autowired
//     private ScheduleJob scheduleJob;

//     @Autowired
//     private DynamicCronExpression expression;

//     @Override
//     public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
  
//         // taskRegistrar.addCronTask(

//         //             ()-> scheduleJob.scheduledTask(), 
                    
//         //             expression.getCron()
//         // );

//         taskRegistrar.addTriggerTask(

//             () -> scheduleJob.scheduledTask(), 
            
//             // triggerContext -> {

//             //     log.info("changed");
//             //     log.info(expression.getCron());
//             //     return new CronTrigger(expression.getCron()).nextExecutionTime(triggerContext); //(expression.getCron())
                
//             // }

//             new Trigger() {
//                 @Override
//                 public Date nextExecutionTime(TriggerContext triggerContext) {
//                     String cron = expression.getCron();
//                     log.info(cron);
//                     CronTrigger trigger = new CronTrigger(cron);
//                     Date nextExec = trigger.nextExecutionTime(triggerContext);
//                     return nextExec;
//                 }
//             }

//         );

//     }
// }
