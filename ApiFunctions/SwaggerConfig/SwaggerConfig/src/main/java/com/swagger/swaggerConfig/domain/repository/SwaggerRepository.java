package com.swagger.swaggerConfig.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.swagger.swaggerConfig.domain.dto.AdmApi;

@Repository
public interface SwaggerRepository extends MongoRepository<AdmApi, String>{

    AdmApi findByApiIdAndSysId(String apiId, String sysId);
    
}
