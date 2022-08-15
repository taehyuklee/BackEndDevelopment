package com.mongoDB.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mongoDB.domain.dto.AdmHndlrDto;
import com.mongoDB.domain.entity.AdmHndlr;
import com.mongoDB.domain.repository.RepositoryHndlr;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //자동주입을 위해서(final -> Constructor를 final을해서 알아서 만들어줌. (아니면 Autowired해줘야 함))
public class ServiceApiHndlr {
    
    private final RepositoryHndlr repositoryHndlr;

    public void createApiHndlr(AdmHndlrDto admHndlrDto){

        AdmHndlr entity = new AdmHndlr(); 
        //원래는 모든 인자를 요구하는데, @NoArgs를 entity Object에 걸어줌으로써 해결 or Builder를 없애줌으로써 해결
    
        BeanUtils.copyProperties(admHndlrDto, entity);


        repositoryHndlr.save(entity);
        
        System.out.println("ApiHndlr가 등록되었습니다");
    }
}
