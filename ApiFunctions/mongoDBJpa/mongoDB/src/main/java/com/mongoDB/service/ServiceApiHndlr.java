package com.mongoDB.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mongoDB.domain.dto.AdmHndlrDto;
import com.mongoDB.domain.entity.AdmApi;
import com.mongoDB.domain.entity.AdmHndlr;
import com.mongoDB.domain.repository.RepositoryApi;
import com.mongoDB.domain.repository.RepositoryHndlr;
import com.mongoDB.domain.repository.RepositoryHndlrGrp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor //자동주입을 위해서(final -> Constructor를 final을해서 알아서 만들어줌. (아니면 Autowired해줘야 함))
public class ServiceApiHndlr {
    
    private final RepositoryHndlr repositoryHndlr;
    private final RepositoryApi repositoryApi;
    private final RepositoryHndlrGrp repositoryHndlrGrp;

    public void createApiHndlr(AdmHndlrDto admHndlrDto){

        AdmHndlr entity = new AdmHndlr(); 
        //원래는 모든 인자를 요구하는데, @NoArgs를 entity Object에 걸어줌으로써 해결 or Builder를 없애줌으로써 해결
    
        BeanUtils.copyProperties(admHndlrDto, entity);


        repositoryHndlr.save(entity);
        
        System.out.println("ApiHndlr가 등록되었습니다");
    }

    public void deleteApiHndlr(String hndlrId){

        var A = repositoryApi.findBySpecReqHndlrHndlr(hndlrId);

        System.out.println(A);

        AdmHndlr admHndlrEntity = repositoryHndlr.findByHndlrId(hndlrId).get();
        
        String objectId = admHndlrEntity.getId();
        String trtSect = admHndlrEntity.getTrtSect();

        Boolean apiBool = false;
        Boolean hndlrGrpBool = false;

        System.out.println(objectId);

        //우선 있는지 hndlrId가 API 아래 HndlrId로 존재하는지 확인하기
       // Boolean trueFalse= repositoryApi.existsBySpecReqHndlrHndlrTrtSect(hndlrId);

        if(trtSect.equals("REQ")){
            System.out.println("Request입니다");
            apiBool = repositoryApi.existsBySpecReqHndlrHndlr(objectId);
            hndlrGrpBool = repositoryHndlrGrp.existsByHndlr(objectId);
            
            System.out.println("API 등록 여부");
            System.out.println(apiBool);
            System.out.println("핸들러 그룹 등록 여부");
            System.out.println(hndlrGrpBool);

            if(apiBool == true || hndlrGrpBool == true){
                log.info("지울수 없습니다");
            }

        }else if(trtSect.equals("RES")){
            System.out.println("Response입니다");
            apiBool = repositoryApi.existsBySpecResHndlrHndlr(objectId);
            hndlrGrpBool = repositoryHndlrGrp.existsByHndlr(objectId);

            System.out.println("API 등록 여부");
            System.out.println(apiBool);
            System.out.println("핸들러 그룹 등록 여부");
            System.out.println(hndlrGrpBool);

            if(apiBool ==true || hndlrGrpBool == true){
                log.info("지울수 없습니다");
            }

        }else if(trtSect.equals("ERR")){
            System.out.println("Error입니다");
            //apiBool = repositoryApi.existsBySpecErrHndlrId(objectId);
            
            System.out.println("API 등록 여부");
            System.out.println(apiBool);
            
            if(apiBool ==true){
                log.info("지울수 없습니다");
            }
        }

        //위의 3개 조건을 모두 통과하면, 어디에도 등록된 핸들러가 아니므로 삭제할수 있다.
        if(apiBool==false && hndlrGrpBool==false){
            System.out.println("지울수 있는 상태입니다");
            repositoryHndlr.deleteById(objectId);
        }


    }
}
