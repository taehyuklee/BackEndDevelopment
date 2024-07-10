package com.ppt.converter.makeslides;

import com.ppt.converter.makeslides.functions.MakeFunctions;
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
            String templateUrl = ""; //"C:/Users/11464/Desktop/template.pptx";
            XMLSlideShow ppt = getSlideInstance(templateUrl, 20);

            // 작업할 Slide 가져오기
            // XSLFSlide slide = ppt.getSlides().get(1);

            TableProperties tableProperties = new TableProperties()
                    .setBold(true).setFillColor(new Integer[]{187, 224, 227}).setBorderColor(new Integer[]{0,0,0})
                    .setColWidth(50).setCenterAlign(true).setFontSize(12d).setBorderThickness(1)
                    .setHeadersNm(new String[] {"col1","col2","col3"}).setNumColumns(3).setNumRows(8)
                    .setGridHeight(30d).setHeaderHeight(30d).setHeaderYn(true).setTablePosition(new Integer[]{300, 100, 190, 166});

            //Table을 생성하고 싶으면 생성하면 된다.
            createTableTargetPpt(tableProperties, ppt, 0);



            // 수정된 PowerPoint 파일 저장
            FileOutputStream fos = new FileOutputStream("C:/Users/11464/Desktop/modified1.pptx");
            ppt.write(fos);
            fos.close();

            ppt.close();
            System.out.println("PowerPoint");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
