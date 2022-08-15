package com.mongoDB.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongoDB.domain.entity.AdmApi;

@Repository
public interface RepositoryApi extends MongoRepository<AdmApi, String>{

    //애시당초 RepositoryAPI가 Interface이기때문에 MongoRepository를 구현하는게 아니라 상속하는게 맞다.
    
}
