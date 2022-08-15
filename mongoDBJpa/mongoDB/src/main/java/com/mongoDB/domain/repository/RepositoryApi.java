package com.mongoDB.domain.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongoDB.domain.entity.AdmApi;
import com.mongoDB.domain.entity.AdmHndlr;

@Repository
public interface RepositoryApi extends MongoRepository<AdmApi, String>{

    //애시당초 RepositoryAPI가 Interface이기때문에 MongoRepository를 구현하는게 아니라 상속하는게 맞다.

    //boolean existsBySpecReqHndlrHndlr(String hndlrId);

    boolean existsBySpecReqHndlrHndlrTrtSect(String hndlrId);

    boolean existsBySpecReqHndlrHndlr(String hndlrId);

    List<AdmHndlr> findBySpecReqHndlrHndlr(String hndlrId);
    
}
