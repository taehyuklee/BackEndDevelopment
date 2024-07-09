package com.web.crawler.cralwer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlingService {

    private HtmlDownloader htmlDownloader;
    private HtmlParser htmlParser;

    public void downAndParser(){

    }


}
