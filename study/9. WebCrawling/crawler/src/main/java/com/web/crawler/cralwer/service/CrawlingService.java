package com.web.crawler.cralwer.service;

import com.web.crawler.cralwer.domain.entity.CollectedUrl;
import com.web.crawler.cralwer.domain.entity.UnCollectedUrl;
import com.web.crawler.cralwer.domain.repository.CollectedUrlRepository;
import com.web.crawler.cralwer.domain.repository.UnCollectedUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CrawlingService extends HtmlParser{

    private HtmlDownloader htmlDownloader;

    private final UnCollectedUrlRepository unCollectedRepository;

    private final CollectedUrlRepository collectRepository;

    @Transactional
    public void registerStartUrl(String startUrl){

        UnCollectedUrl unCollectedUrl = new UnCollectedUrl();

        unCollectedUrl.setUrl(startUrl).setCollected(false);

        unCollectedRepository.save(unCollectedUrl);

    }

    @Transactional
    public void collectUrlInHtml(){

        UnCollectedUrl unCollectedUrl = unCollectedRepository.findTop1ByOrderByCretDtDesc();

        String targetUrl = unCollectedUrl.getUrl();

        String htmlContents = downloadHTML(targetUrl);

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



        //collectedUrl로 바꿔줘야한다.
        CollectedUrl collectedUrl = new CollectedUrl();
        unCollectedUrl.setCollected(true); //collect여부 true로 바꿔주고
        BeanUtils.copyProperties(unCollectedUrl, collectedUrl, "id");
        collectRepository.save(collectedUrl);
        //수집됐으니까 이제 지워준다
        unCollectedRepository.delete(unCollectedUrl);

    }


    //Test용 API
    @Transactional
    public void saveCollectedUrl(){

        UnCollectedUrl url = unCollectedRepository.findTop1ByOrderByCretDtDesc();

        //여기서 url관련 중복체크 해줘야 한다.
        url.setCollected(true);

        //collectedUrl로 바꿔줘야한다.
        CollectedUrl collectedUrl = new CollectedUrl();
        BeanUtils.copyProperties(url, collectedUrl, "id");
        unCollectedRepository.delete(url);

        collectRepository.save(collectedUrl);

        System.out.println(url);



    }


}
