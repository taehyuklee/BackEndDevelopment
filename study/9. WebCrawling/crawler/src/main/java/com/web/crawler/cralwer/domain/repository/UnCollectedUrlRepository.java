package com.web.crawler.cralwer.domain.repository;

import com.web.crawler.cralwer.domain.entity.UnCollectedUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UnCollectedUrlRepository extends MongoRepository<UnCollectedUrl, String> {

    UnCollectedUrl findTop1ByOrderByCretDtDesc();

}
