package com.scheduler.shedLock.config.expression;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class DynamicCronExpression {

    private String cron = "*/15 * * * * ?";
    
}
