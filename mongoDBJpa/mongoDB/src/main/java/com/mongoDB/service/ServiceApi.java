package com.mongoDB.service;

import java.io.File;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongoDB.domain.dto.AdmApiDto;
import com.mongoDB.domain.entity.AdmApi;
import com.mongoDB.domain.repository.RepositoryApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceApi {

    private final RepositoryApi repositoryApi;

    public void createApi(AdmApiDto admApiDto){

        AdmApi entity = new AdmApi(); 
        //원래는 모든 인자를 요구하는데, @NoArgs를 entity Object에 걸어줌으로써 해결 or Builder를 없애줌으로써 해결
    
        BeanUtils.copyProperties(admApiDto, entity);

        repositoryApi.save(entity);
        
        System.out.println("Api가 등록되었습니다");
    }

    public void createApiWithFile(AdmApiDto admApiDto, MultipartFile jsonFile){

        AdmApi entity = new AdmApi(); 
        //원래는 모든 인자를 요구하는데, @NoArgs를 entity Object에 걸어줌으로써 해결 or Builder를 없애줌으로써 해결

        admApiDto.getSpec().get(0).setSwaggerSpec(jsonFile);
    
        BeanUtils.copyProperties(admApiDto, entity);

        repositoryApi.save(entity);
        
        System.out.println("Api이 파일과 함께 등록되었습니다");
    }
    
}
