package download.excel.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import download.excel.domain.dto.ListDto;
import download.excel.service.ServiceExcel;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Controller{

    private final ServiceExcel serviceExcel;

    @PostMapping("/create")
    public void save(@RequestBody ListDto dto){
        serviceExcel.save(dto);

    }

    @GetMapping("/excel/download")
    public void download(HttpServletResponse response) throws IOException{
        System.out.println("1`221211121");
        serviceExcel.download(response);
    }


}