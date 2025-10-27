package com.error.generator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StackOverflowService {
    private static final Logger logger = LoggerFactory.getLogger(StackOverflowService.class);

    public void causeStackOverflow() {
        logger.debug("Stack call...");
        causeStackOverflow(); // 무한 재귀
    }
}