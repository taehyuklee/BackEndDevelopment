package com.web.crawler.cralwer.service;

import com.web.crawler.cralwer.domain.entity.CollectedUrl;
import com.web.crawler.cralwer.domain.entity.UnCollectedUrl;
import com.web.crawler.cralwer.domain.repository.CollectedUrlRepository;
import com.web.crawler.cralwer.domain.repository.UnCollectedUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CrawlingService extends HtmlParser{

    private HtmlDownloader htmlDownloader;

    private final UnCollectedUrlRepository unCollectedRepository;

    private final CollectedUrlRepository collectRepository;

    public void collectUrlInHtml(){

        String startUrl = "https://taehyuklee.tistory.com/";

        String htmlContents = downloadHTML(startUrl);

        //jsoup을 이용해서 내부 url을 모두 가져온다.
        Set<String> urls = extractURLs(htmlContents);
        System.out.println(urls);
        List<String> urlList = new ArrayList<>(urls);

        //List Url을 보기
        List<UnCollectedUrl> entityList = new ArrayList<>();
        for(String url : urlList){
            UnCollectedUrl urlEntity = new UnCollectedUrl().setUrl(url).setCollected(false);
            entityList.add(urlEntity);
        }

        //중복 제거 Logic넣어야함.
        
        unCollectedRepository.saveAll(entityList);

    }

    public void saveCollectedUrl(){

        UnCollectedUrl url = unCollectedRepository.findTop1ByOrderByCretDtDesc();
        System.out.println(url);


    }


}
