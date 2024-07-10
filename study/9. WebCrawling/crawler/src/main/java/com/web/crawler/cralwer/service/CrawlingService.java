package com.web.crawler.cralwer.service;

import com.web.crawler.cralwer.domain.entity.Url;
import com.web.crawler.cralwer.domain.repository.DomainRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CrawlingService extends HtmlParser{

    private HtmlDownloader htmlDownloader;

    private final DomainRepository repository;

    public void downAndParser(){

        String startUrl = "https://www.naver.com/";

        String htmlContents = downloadHTML(startUrl);

        //jsoup을 이용해서 내부 url을 모두 가져온다.
        Set<String> urls = extractURLs(htmlContents);
        List<String> urlList = new ArrayList<>(urls);

        //List Url을 보기
        List<Url> entityList = new ArrayList<>();
        for(String url : urlList){
            Url urlEntity = new Url().setUrl(url).setCollected(true);
            entityList.add(urlEntity);
        }

        repository.saveAll(entityList);


    }


}
