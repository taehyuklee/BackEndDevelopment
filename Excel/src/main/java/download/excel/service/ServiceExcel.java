package download.excel.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import download.excel.domain.dto.ListDto;
import download.excel.domain.entity.ListEntity;
import download.excel.repository.ExcelRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ServiceExcel {

    private final ExcelRepository excelRepository;
    
    //Create
    public void save(ListDto dto){
        ListEntity entity = new ListEntity();
        BeanUtils.copyProperties(dto, entity);

        excelRepository.save(entity);
    }

    //DownLoad
    public void download(HttpServletResponse response) throws IOException{

        //http header에 content-type설정해주기

        System.out.println("1`221211121");

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");

        //필요한 데이터를 우선 조회한다
        List<ListEntity> listSearch = excelRepository.findByGampangE("jiPangE");
        System.out.println("1`221211121");

        System.out.println(listSearch);

        Workbook workbook = new HSSFWorkbook();
        Workbook wb = new XSSFWorkbook();

        Sheet sheet = wb.createSheet("fist Sheet");
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        System.out.println("excel header");
        // Excel Header
        row = sheet.createRow(rowNum++); //row 숫자를 증가시킨다
        cell = row.createCell(0);
        cell.setCellValue("humidity");
        cell = row.createCell(1);
        cell.setCellValue("gorani");
        cell = row.createCell(2);
        cell.setCellValue("rain");
        cell = row.createCell(3);
        cell.setCellValue("gampanE");
        
        System.out.println("excel body");
        //Excel Body
        for(int i=0; i<listSearch.size(); i++){

            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(listSearch.get(i).getHumidity());
            cell = row.createCell(1);
            cell.setCellValue(listSearch.get(i).getGorani());
            cell = row.createCell(2);
            cell.setCellValue(listSearch.get(i).getRain()); //boolean primitive는 getter method가 만들어지지 않아 reference type으로 바꿔줌
            cell = row.createCell(3);
            cell.setCellValue(listSearch.get(i).getGampangE());

        }

        System.out.println("file out");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
        System.out.println("finish");

    }

    
}
