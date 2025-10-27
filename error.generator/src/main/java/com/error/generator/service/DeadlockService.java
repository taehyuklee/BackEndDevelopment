package com.error.generator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeadlockService {
    private static final Logger logger = LoggerFactory.getLogger(DeadlockService.class);

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void causeDeadlock() {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                logger.info("Thread 1: holding lock1...");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                synchronized (lock2) {
                    logger.info("Thread 1: holding lock2...");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                logger.info("Thread 2: holding lock2...");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                synchronized (lock1) {
                    logger.info("Thread 2: holding lock1...");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
