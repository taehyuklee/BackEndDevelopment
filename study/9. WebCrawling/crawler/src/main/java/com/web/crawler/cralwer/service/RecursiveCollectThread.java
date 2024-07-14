package com.web.crawler.cralwer.service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class RecursiveCollectThread extends Thread{

    private final CrawlingService crawlingService;


    @Override
    public void run(){

        while(true){
            crawlingService.collectUrlInHtml();
        }

    }

}
