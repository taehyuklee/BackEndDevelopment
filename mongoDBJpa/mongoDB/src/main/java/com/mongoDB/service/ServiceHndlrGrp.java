package com.mongoDB.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mongoDB.domain.dto.AdmHndlrGrpDto;
import com.mongoDB.domain.entity.AdmHndlrGrp;
import com.mongoDB.domain.repository.RepositoryHndlrGrp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceHndlrGrp {

    private final RepositoryHndlrGrp repositoryHndlrGrp;

    public void createApiHndlrGrp(AdmHndlrGrpDto admHndlrGrpDto){

        AdmHndlrGrp entity = new AdmHndlrGrp(); 
        //원래는 모든 인자를 요구하는데, @NoArgs를 entity Object에 걸어줌으로써 해결 or Builder를 없애줌으로써 해결
    
        BeanUtils.copyProperties(admHndlrGrpDto, entity);

        repositoryHndlrGrp.save(entity);
        
        System.out.println("ApiHndlrGrp에 등록되었습니다");
    }
    
}
