package com.web.crawler.cralwer.service;

import com.web.crawler.cralwer.domain.entity.UnCollectedUrl;
import com.web.crawler.cralwer.domain.repository.UnCollectedUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CrawlingService extends HtmlParser{

    private HtmlDownloader htmlDownloader;

    private final UnCollectedUrlRepository repository;

    public void collectUrlInHtml(){

        String startUrl = "https://www.naver.com/";

        String htmlContents = downloadHTML(startUrl);

        //jsoup을 이용해서 내부 url을 모두 가져온다.
        Set<String> urls = extractURLs(htmlContents);
        List<String> urlList = new ArrayList<>(urls);

        //List Url을 보기
        List<UnCollectedUrl> entityList = new ArrayList<>();
        for(String url : urlList){
            UnCollectedUrl urlEntity = new UnCollectedUrl().setUrl(url).setCollected(false);
            entityList.add(urlEntity);
        }

        repository.saveAll(entityList);


    }


}
