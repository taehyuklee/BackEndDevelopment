package com.ppt.converter.makeslides;

import com.ppt.converter.makeslides.functions.MakeFunctions;
import com.ppt.converter.makeslides.table_props.ImageProperties;
import com.ppt.converter.makeslides.table_props.TableProperties;
import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class MakePPT extends MakeFunctions{

    public void makePptx(String[][] data) {

        try {
            //새로운 pptx Instance생성 또는 기존 Template가져다 쓰기
            String templateUrl = "C:/Users/11464/Desktop/template.pptx";
            XMLSlideShow ppt = getSlideInstance(templateUrl, 20);

            //내가 만들 Table정보들 관련해서 기입하기
            TableProperties tableProperties = new TableProperties()
                    .setBold(true).setFillColor(new Integer[]{187, 224, 227}).setBorderColor(new Integer[]{0,0,0})
                    .setColWidth(150).setCenterAlign(true).setFontSize(24d).setBorderThickness(1)
                    .setHeadersNm(new String[] {"col1","col2","col3", "col4"}).setNumColumns(4).setNumRows(8)
                    .setGridHeight(30d).setHeaderHeight(30d).setHeaderYn(true).setTablePosition(new Integer[]{90, 100, 190, 166});

            //Table을 생성하고 싶으면 생성하면 된다.
            createTableTargetPpt(tableProperties, ppt, 0);

            //기존의 Data를 만든 table에 mapping하고싶다.
            mappingTable(ppt, 0, data);
            
            //Image를 넣는다
            //이미지 경로파일을 가져온다, Specify the image file path
            String imagePath = "C:/Users/11464/Desktop/jmp pdf/jml기능.JPG";
            ImageProperties imageProperties = new ImageProperties().setImgUrl(imagePath);
            putImage(imageProperties, ppt, 1);


            //saveFile
            String savePath = "C:/Users/11464/Desktop/modified1.pptx";
            saveFile(ppt, savePath);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
