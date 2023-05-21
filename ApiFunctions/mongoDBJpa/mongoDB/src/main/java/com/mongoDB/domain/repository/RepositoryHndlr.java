package com.mongoDB.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongoDB.domain.entity.AdmHndlr;

@Repository
public interface RepositoryHndlr extends MongoRepository<AdmHndlr, String> {

    Optional<AdmHndlr> findByHndlrId(String objectId);
    
}
