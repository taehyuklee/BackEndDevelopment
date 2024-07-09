package com.web.crawler.cralwer.domain.repository;

import com.web.crawler.cralwer.domain.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DomainRepository extends MongoRepository<Url, String> {

}
