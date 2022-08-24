package bulletinBoard.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bulletinBoard.notice.domain.dto.NoticeDto;
import bulletinBoard.notice.domain.dto.NoticeQueryParameter;
import bulletinBoard.notice.service.BulletinService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BulletinController {

    private final BulletinService bulletinService;

    //Create
    @PostMapping(value ="/createBulletin", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String createBulletin(@RequestPart("notice") NoticeDto noticeDto, @RequestPart("imgFile") List<MultipartFile> imgFile) throws IOException{
        return bulletinService.createBulletin(noticeDto, imgFile);
    }


    //Read
    //전체조회
    @GetMapping(value = "/getAllNotice")
    public List<NoticeDto> getAllBulletinList(Pageable pageable, NoticeQueryParameter noticeQueryParameter){
        return bulletinService.getAllBulletinList(pageable, noticeQueryParameter);
    }


    //상세조회
    @GetMapping(value = "/getNoticeDetail")
    public NoticeDto getBulletinDetail(String title){
        return bulletinService.getBulletinDetail(title);
    }

    //Update
    @PostMapping(value = "/updateBulletin", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String updateBulletin(@RequestPart("notice") NoticeDto noticeDto, @RequestPart("imgFile") List<MultipartFile> imgFile) throws IOException{
        return bulletinService.updateBulletin(noticeDto, imgFile);
    }


    //Delete
    @DeleteMapping(value ="/delete")
    public void deleteBulletin(@RequestParam("title") String objectId){
        bulletinService.deleteBulletin(objectId);
    }

    //DownLoad File
    public void downloadBulletin(){
    }


    
}
