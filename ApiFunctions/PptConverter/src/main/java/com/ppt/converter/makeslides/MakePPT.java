package com.ppt.converter.makeslides;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTableRow;

public class MakePPT {

    public void makePptx() {
        try {
            // PowerPoint 템플릿 파일 경로
            String templatePath = "C:/Users/11464/Desktop/template.pptx";

            // PowerPoint 템플릿 파일 읽기
            FileInputStream fis = new FileInputStream(templatePath);
            XMLSlideShow ppt = new XMLSlideShow(fis);
            fis.close();

            // 첫 번째 슬라이드 가져오기 (일반적으로 첫 번째 슬라이드에 테이블이 있을 것으로 가정)
            XSLFSlide slide = ppt.getSlides().get(0);

            // 슬라이드에서 첫 번째 테이블 가져오기 (일반적으로 템플릿에는 하나의 테이블만 있을 것으로 가정)
            XSLFTable table = null;
            for (var shape : slide.getShapes()) {
                if (shape instanceof XSLFTable) {
                    table = (XSLFTable) shape;
                    break;
                }
            }

            if (table == null) {
                System.out.println("테이블을 찾을 수 없습니다.");
                return;
            }

            // 임의의 데이터로 테이블 채우기 (예시 데이터)
            String[][] data = {
                    {"이름", "나이", "직업", "성별"},
                    {"홍길동", "30", "개발자", "남"},
                    {"김철수", "25", "디자이너"},
                    {"박영희", "28", "마케터", "여"}
            };

            // 테이블에 데이터 채우기
            for (int i = 0; i < data.length; i++) {
                XSLFTableRow row = (i < table.getNumberOfRows()) ? table.getRows().get(i) : table.addRow();
                for (int j = 0; j < data[i].length; j++) {
                    XSLFTableCell cell = (j < row.getCells().size()) ? row.getCells().get(j) : row.addCell();
                    cell.setText(data[i][j]);
                }
            }

            // 수정된 PowerPoint 파일 저장
            FileOutputStream fos = new FileOutputStream("C:/Users/11464/Desktop/modified.pptx");
            ppt.write(fos);
            fos.close();

            ppt.close();
            System.out.println("PowerPoint 템플릿을 성공적으로 수정하였습니다.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
