package com.web.crawler.cralwer.controller;

import com.web.crawler.cralwer.service.CrawlingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingService crawlingService;

    @GetMapping("/go")
    public void downAndParser(){
        crawlingService.collectUrlInHtml();
    }

    @GetMapping("/go2")
    public void saveUrl(){
        crawlingService.saveCollectedUrl();
    }

}
