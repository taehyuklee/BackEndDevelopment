package com.mongoDB.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongoDB.domain.entity.AdmHndlrGrp;

@Repository
public interface RepositoryHndlrGrp extends MongoRepository<AdmHndlrGrp, String>{

    boolean existsByHndlr(String hndlrId);
    
}
