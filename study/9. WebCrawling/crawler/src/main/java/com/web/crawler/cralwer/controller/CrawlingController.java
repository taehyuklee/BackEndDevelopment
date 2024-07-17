package com.web.crawler.cralwer.controller;

import com.web.crawler.cralwer.service.CrawlingService;
import com.web.crawler.cralwer.service.RecursiveCollectThread;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingService crawlingService;



    @GetMapping("/startUrl")
    public void startUrl(@RequestParam(name="startUrl") String startUrl){
        crawlingService.registerStartUrl(startUrl);
    }

    @GetMapping("/recursiveCollect")
    public void downAndParser(){
        crawlingService.collectUrlInHtml();
    }

    @GetMapping("/collectUrl")
    public void saveUrl(){
        crawlingService.saveCollectedUrl();
    }

    @GetMapping("/startCollect")
    public void go(){

        Thread thread = new RecursiveCollectThread(crawlingService);

        thread.start();

    }

}
