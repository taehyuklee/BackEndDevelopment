package bulletinBoard.notice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bulletinBoard.notice.domain.dto.ImgFile;
import bulletinBoard.notice.domain.dto.ImgFileDto;
import bulletinBoard.notice.domain.dto.NoticeDto;
import bulletinBoard.notice.domain.dto.NoticeQueryParameter;
import bulletinBoard.notice.domain.entity.Notice;
import bulletinBoard.notice.domain.entity.NoticeMultipartFile;
import bulletinBoard.notice.domain.repository.BulletinRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BulletinService {

    private final BulletinRepository bulletinRepository;


    public String createBulletin(NoticeDto noticeDto, List<MultipartFile> imgFileList) throws IOException{

        Notice notice = new Notice();

        BeanUtils.copyProperties(noticeDto, notice, "imgFile");

        //type에 대한 분기처리 해줘야 한다. 
        String type = noticeDto.getType();
        if(type.equals("img")){

            //ImgFile 객체에 mapping해주는 부분 필요함
            List<ImgFile> imgFileEntityList = new ArrayList<>();
            for(MultipartFile imgFile : imgFileList){
                ImgFile imgFileEntity = new ImgFile();
                imgFileEntity.setBytes(imgFile.getBytes()).setContentType(imgFile.getContentType())
                            .setFileName(imgFile.getName()).setOriginalFileName(imgFile.getOriginalFilename())
                            .setSize(imgFile.getSize());
                imgFileEntityList.add(imgFileEntity);
            }

            notice.setImgFile(imgFileEntityList);
        }
        

        bulletinRepository.save(notice);

        return "공지사항 등록에 성공했습니다";
    }

    //전체조회
    //API 정보 전체조회 및 검색
    public List<NoticeDto> getAllBulletinList(Pageable pageable, NoticeQueryParameter noticeQueryParameter) {

        if (noticeQueryParameter.getTitle() != null || noticeQueryParameter.getContent() != null) {
            if (noticeQueryParameter.getTitle() != null) {
                var pageNotice = (Page) bulletinRepository.findByTitleIgnoreCaseContaining(noticeQueryParameter.getTitle(), pageable);
                List<NoticeDto> entityList = pageNotice.getContent();
                return entityList;
            }
            if (noticeQueryParameter.getContent() != null) {
                var pageNotice = (Page) bulletinRepository.findByTitleIgnoreCaseContaining(noticeQueryParameter.getContent(), pageable);
                List<NoticeDto> entityList = pageNotice.getContent();
                return entityList;
            }
        }

        var pageNotice = (Page) bulletinRepository.findAll(pageable);
        List<NoticeDto> entityList = pageNotice.getContent();
        return entityList;
    }



    //상세조회
    public NoticeDto getBulletinDetail(String title){

        Notice noticeEntity = bulletinRepository.findByTitle(title);

        NoticeDto noticeDto = new NoticeDto();
        BeanUtils.copyProperties(noticeEntity, noticeDto, "imgFile"); //imgFile빼고는 모두 여기서 복사한다.


        List<ImgFileDto> ImgSource = new ArrayList<ImgFileDto>();

        for(int i = 0; i<noticeEntity.getImgFile().size(); i++){
            ImgFileDto imgFileDto = new ImgFileDto();

            ImgFile eachImg = noticeEntity.getImgFile().get(i);
            String imgBytesBase64 = Base64.getEncoder().encodeToString(eachImg.getBytes());

            //확장자 저장해주기 
            String contentType = eachImg.getContentType();
            String extension = contentType.substring(contentType.lastIndexOf("/")+1);


            imgFileDto.setContentType(noticeEntity.getImgFile().get(i).getContentType())
                        .setFileName(noticeEntity.getImgFile().get(i).getFileName())
                        .setOriginalFileName(noticeEntity.getImgFile().get(i).getOriginalFileName())
                        .setSize(noticeEntity.getImgFile().get(i).getSize())
                        .setImgBytes(imgBytesBase64)
                        .setExtension(extension);

            ImgSource.add(imgFileDto);

        }

        noticeDto.setImgFile(ImgSource);

        return noticeDto;
        
    }

    //업데이트
    public String updateBulletin(NoticeDto noticeDto, List<MultipartFile> imgFileList) throws IOException{

        String title = noticeDto.getTitle();

        Notice noticeEntity = bulletinRepository.findByTitle(title);

        BeanUtils.copyProperties(noticeDto, noticeEntity, "imgFile");

        //type에 대한 분기처리 해줘야 한다. 
        String type = noticeDto.getType();
        if(type.equals("img")){

            //ImgFile 객체에 mapping해주는 부분 필요함
            List<ImgFile> imgFileEntityList = new ArrayList<>();
            for(MultipartFile imgFile : imgFileList){
                ImgFile imgFileEntity = new ImgFile();
                imgFileEntity.setBytes(imgFile.getBytes()).setContentType(imgFile.getContentType())
                            .setFileName(imgFile.getName()).setOriginalFileName(imgFile.getOriginalFilename())
                            .setSize(imgFile.getSize());
                imgFileEntityList.add(imgFileEntity);
            }

            noticeEntity.setImgFile(imgFileEntityList);
        }
        
        bulletinRepository.save(noticeEntity);

        return "업데이트 되었습니다";
    }

    //삭제
    public void deleteBulletin(String objectId){
        bulletinRepository.deleteById(objectId);
    }
    
}
