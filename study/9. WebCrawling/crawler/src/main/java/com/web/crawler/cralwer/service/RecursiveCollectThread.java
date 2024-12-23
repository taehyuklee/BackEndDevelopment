package com.web.crawler.cralwer.service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class RecursiveCollectThread extends Thread{

    private final CrawlingService crawlingService;


    @Override
    public void run(){

        while(true){

            try {
                crawlingService.collectUrlInHtml();
                Thread.sleep(1500);
                System.out.println("수집중입니다.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
