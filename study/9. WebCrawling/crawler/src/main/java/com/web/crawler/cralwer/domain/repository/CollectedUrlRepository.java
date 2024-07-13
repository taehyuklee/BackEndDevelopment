package com.web.crawler.cralwer.domain.repository;

import com.web.crawler.cralwer.domain.entity.CollectedUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectedUrlRepository extends MongoRepository<CollectedUrl, String> {


}
