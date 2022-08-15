package com.mongoDB.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mongoDB.domain.dto.AdmHndlrDto;
import com.mongoDB.domain.entity.AdmApi;
import com.mongoDB.domain.entity.AdmHndlr;
import com.mongoDB.domain.repository.RepositoryApi;
import com.mongoDB.domain.repository.RepositoryHndlr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor //자동주입을 위해서(final -> Constructor를 final을해서 알아서 만들어줌. (아니면 Autowired해줘야 함))
public class ServiceApiHndlr {
    
    private final RepositoryHndlr repositoryHndlr;
    private final RepositoryApi repositoryApi;

    public void createApiHndlr(AdmHndlrDto admHndlrDto){

        AdmHndlr entity = new AdmHndlr(); 
        //원래는 모든 인자를 요구하는데, @NoArgs를 entity Object에 걸어줌으로써 해결 or Builder를 없애줌으로써 해결
    
        BeanUtils.copyProperties(admHndlrDto, entity);


        repositoryHndlr.save(entity);
        
        System.out.println("ApiHndlr가 등록되었습니다");
    }

    public void deleteApiHndlr(String hndlrId){

        String objectId = repositoryHndlr.findByHndlrId(hndlrId).get().getId();

        System.out.println(objectId);

        //우선 있는지 hndlrId가 API 아래 HndlrId로 존재하는지 확인하기
       // Boolean trueFalse= repositoryApi.existsBySpecReqHndlrHndlrTrtSect(hndlrId);

        Boolean trueFalse11 = repositoryApi.existsBySpecReqHndlrHndlr(hndlrId);

        List<AdmHndlr> listAdm = repositoryApi.findBySpecReqHndlrHndlr(objectId);

        System.out.println(trueFalse11);

        System.out.println(listAdm.toString()); 


        //만약 있으면 error처리

        //만약 없으면 다 지워주는거 TB서버에 있는 DPLY있는거


        System.out.println("여기는 이제 완전탐색");

        List<AdmApi> allApi = repositoryApi.findAll();
        System.out.println("check1");

        AdmHndlr admHndlr = repositoryHndlr.findByHndlrId(hndlrId).get();
        System.out.println("check2");

        String trtSect = admHndlr.getTrtSect();
        System.out.println("check3");

        System.out.println(allApi.toString());
        System.out.println(admHndlr.toString());
        System.out.println(trtSect.toString());
        
        if(allApi.size() != 0){
            if(trtSect.equals("REQ")){
                //요청 핸들러일때

                //모든 api들 다 돌면서 
                for (int i=0 ; i<allApi.size(); i++){

                    System.out.println("1");

                    //그 api안의 모든 버전들을 돌면서
                    for(int j=0; j<allApi.get(i).getSpec().size(); j++){

                        System.out.println("2");

                        //핸들러 그룹 아이디
                        List<AdmHndlr> admHndlrList= allApi.get(i).getSpec().get(j).getReqHndlr().getHndlr();
                        List<AdmHndlr> admHndlrGrpList = allApi.get(i).getSpec().get(j).getReqHndlr().getHndlrGrp().getHndlr();

                        for(AdmHndlr admHndlrob : admHndlrList){
                            if(hndlrId.equals(admHndlrob.getHndlrId())){
                                log.info("지울수 없습니다");//throw new AdminRuntimeException(StatusEnum.REGED_HNDLR);
                            }
                        }

                        for(AdmHndlr admHndlrob : admHndlrGrpList){
                            if(hndlrId.equals(admHndlrob.getHndlrId())){
                                log.info("지울수 없습니다");//throw new AdminRuntimeException(StatusEnum.REGED_HNDLR);
                            }
                        }
                    }
                }


            }else if(trtSect.equals("RES")){
                //응답 핸들러일때

                System.out.println("RES입니다");

                //모든 api들 다 돌면서 
                for (int i=0 ; i<allApi.size(); i++){

                    //그 api안의 모든 버전들을 돌면서
                    for(int j=0; j<allApi.get(i).getSpec().size(); j++){

                        //핸들러 그룹 아이디
                        List<AdmHndlr> admHndlrList= allApi.get(i).getSpec().get(j).getResHndlr().getHndlr();
                        List<AdmHndlr> admHndlrGrpList = allApi.get(i).getSpec().get(j).getResHndlr().getHndlrGrp().getHndlr();

                        for(AdmHndlr admHndlrob : admHndlrList){
                            if(hndlrId.equals(admHndlrob.getHndlrId())){
                                log.info("지울수 없습니다");//throw new AdminRuntimeException(StatusEnum.REGED_HNDLR);
                            }
                        }

                        for(AdmHndlr admHndlrob : admHndlrGrpList){
                            if(hndlrId.equals(admHndlrob.getHndlrId())){
                                log.info("지울수 없습니다");//throw new AdminRuntimeException(StatusEnum.REGED_HNDLR);
                            }
                        }
                    }
                }

            }else if(trtSect.equals("ERR")){
                //에러 핸들러일때

                for (int i=0 ; i<allApi.size(); i++){

                    //그 api안의 모든 버전들을 돌면서
                    for(int j=0; j<allApi.get(i).getSpec().size(); j++){

                        //핸들러 그룹 아이디
                        if(hndlrId.equals(allApi.get(i).getSpec().get(j).getErrHndlr().getHndlrId())){
                            log.info("지울수 없습니다");//throw new AdminRuntimeException(StatusEnum.REGED_HNDLR);
                        } 
                    }
                }
            }
        }

        // TODO: 사용하는게 없을 경우 배포테이블 TB에서 상태(dplyType) DEL로 변경




    }
}
