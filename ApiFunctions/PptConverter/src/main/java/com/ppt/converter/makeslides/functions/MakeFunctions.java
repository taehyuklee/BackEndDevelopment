package com.ppt.converter.makeslides.functions;

import com.ppt.converter.makeslides.table_props.ImageProperties;
import com.ppt.converter.makeslides.table_props.TableProperties;
import lombok.NoArgsConstructor;
import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@NoArgsConstructor
public class MakeFunctions {

    /**
     * Slide instance를 내가 원하는 page숫자를 가지고 만들어주는 method
     **/
    public XMLSlideShow getSlideInstance(String templatePath, int addPageNum) throws IOException {

        XMLSlideShow ppt = null;

        if(!templatePath.isEmpty()) {
            // PowerPoint 템플릿 파일 읽기
            FileInputStream fis = new FileInputStream(templatePath);
            ppt = new XMLSlideShow(fis);
            fis.close();
        }else{
            // PowerPoint 템플릿을 만든다
            ppt = new XMLSlideShow();
        }

        putSlidesIntoInstance(ppt, addPageNum);

        return ppt;
    }

    // pptx 인스턴스에 Slide를 추가하는 method
    public void putSlidesIntoInstance(XMLSlideShow pptInstance, int addPageNum){
        for(int i=0; i<addPageNum; i++){
            pptInstance.createSlide();
        }
    }

    /**  특정 Slide에 createTable을 한다.
     *  pageIndex는 0부터시작합니다. powerpoint자체의
     *  page Index start from 0
     */
    public void createTableTargetPpt(TableProperties tableProps, XMLSlideShow pptInstance, int pageIndex){

        //해당 pptSlide의 Slides를 List로 반환한다.
        List<XSLFSlide> slides = pptInstance.getSlides();

        XSLFSlide slide = slides.get(pageIndex);

        //표 생성하기
        XSLFTable pptTable = slide.createTable();

        //생성된 표 위치시키기 (Anchor method가 무언가를 위치시킬때 사용된다)
        Integer[] positionInfo = tableProps.getTablePosition();
        pptTable.setAnchor(new Rectangle(positionInfo[0], positionInfo[1],positionInfo[2],positionInfo[3]));

        //header생성
        if(tableProps.getHeaderYn()){
            XSLFTableRow tableHeader = pptTable.addRow();
            tableHeader.setHeight(tableProps.getHeaderHeight());
            
            //header Column명들 가져온다
            String[] columnsNms = tableProps.getHeadersNm();

            //Header에 Column들 넣는다.
            for(int i=0; i<tableProps.getNumColumns(); i++){
                XSLFTableCell cell = tableHeader.addCell();

                Integer[] fillColors = tableProps.getFillColor();
                cell.setFillColor(new Color(fillColors[0], fillColors[1], fillColors[2])); //Header에 Color넣기

                Integer[] borderColors = tableProps.getBorderColor();
                cell.setBorderColor(TableCell.BorderEdge.top, new Color(borderColors[0], borderColors[1], borderColors[2]));
                cell.setBorderColor(TableCell.BorderEdge.right, new Color(borderColors[0], borderColors[1], borderColors[2]));
                cell.setBorderColor(TableCell.BorderEdge.bottom, new Color(borderColors[0], borderColors[1], borderColors[2]));
                cell.setBorderColor(TableCell.BorderEdge.left, new Color(borderColors[0], borderColors[1], borderColors[2]));

                Integer borderThick = tableProps.getBorderThickness();
                cell.setBorderWidth(TableCell.BorderEdge.top, borderThick);
                cell.setBorderWidth(TableCell.BorderEdge.right, borderThick);
                cell.setBorderWidth(TableCell.BorderEdge.bottom, borderThick);
                cell.setBorderWidth(TableCell.BorderEdge.left, borderThick);

                // Cell정렬 여부
                XSLFTextParagraph paragraph = cell.addNewTextParagraph();
                if(tableProps.getCenterAlign()){
                    paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                }

                // Font 설정
                XSLFTextRun textRun = paragraph.addNewTextRun();
                textRun.setBold(tableProps.getBold());

                //기본적으로 0,0,0이다.
                textRun.setFontColor(new Color(0,0,0));
                textRun.setFontSize(tableProps.getFontSize());

                // header width
                pptTable.setColumnWidth(i, tableProps.getColWidth());
                
                // header column명 기입
                /* getCell인자는 row, col순으로 되어 있다 */
                pptTable.getCell(0,i).getTextParagraphs()
                        .get(0).getTextRuns().get(0).setText(columnsNms[i]);

            }

        }

        // dataTable생성 (header로직 이후)
        for(int i=0; i<tableProps.getNumRows(); i++){
            XSLFTableRow tableRow = pptTable.addRow();
            tableRow.setHeight(tableProps.getGridHeight());

        }

    }


    //기존 Template을 가져와서 table에 mapping하는 방법
    public void mappingTable(XSLFSlide slide, String[][] data){

        // 슬라이드에서 첫 번째 테이블 가져오기 (일반적으로 템플릿에는 하나의 테이블만 있을 것으로 가정)
        XSLFTable table = null;
        for (var shape : slide.getShapes()) {
            if (shape instanceof XSLFTable) {
                table = (XSLFTable) shape;
                break;
            }
        }

        if (table == null) {
            System.out.println("NoTable");
            return;
        }

        int n=1, m=1;
        // 테이블에 데이터 채우기
        for (int i = 0; i < data.length; i++) {
            XSLFTableRow row = (i < table.getNumberOfRows()) ? table.getRows().get(n) : table.addRow();

            for (int j = 0; j < data[i].length; j++) {

                XSLFTableCell cell = (j < row.getCells().size()) ? row.getCells().get(m) : row.addCell();

                m++;
                if (cell.isMerged() || cell.getText() != null && !cell.getText().isEmpty()) {
                    continue;
                }

                cell.setText(data[i][j]);
            }
            n++;
            m=0;
        }


//        for(int i=0; i< data.length; i++){
//
//            for(int j=0; j<data[i].length; j++){
//
//                for(int n=0; j<table.getNumberOfRows(); n++){
//
//                    XSLFTableRow row = table.getRows().get(n);
//
//                    for(int m=0; m<row.getCells().size(); m++){
//
//                        XSLFTableCell cell = row.getCells().get(m);
//                        if (cell.isMerged() || cell.getText() != null && !cell.getText().isEmpty()) {
//                            continue;
//                        }
//
//                        cell.setText(data[i][j]);
//
//                    }
//
//                }
//
//            }
//        }


    }

    /***
     * 이미지 삽입
     */
    public void putImage(ImageProperties imgProps, XMLSlideShow ppt, int pageIndex) throws Exception {

        //Slide를 만든다. Create a slide
        XSLFSlide slide = ppt.getSlides().get(pageIndex);

        // Load the image into a byte array
        byte[] pictureData = loadImage(imgProps.getImgUrl());

        // Add the picture to the presentation
        XSLFPictureData pd = ppt.addPicture(pictureData, XSLFPictureData.PictureType.JPEG);

        // Create a picture shape
        XSLFPictureShape picture = slide.createPicture(pd);

        // Set position and size of the picture
        picture.setAnchor(new java.awt.Rectangle(100, 100, 400, 300));

        // Save the presentation
        FileOutputStream out = new FileOutputStream("C:/Users/11464/Desktop/output.pptx");
        ppt.write(out);
        out.close();

        System.out.println("Presentation created successfully");

    }

    // Load an image from file path
    private static byte[] loadImage(String imagePath) throws Exception {
        FileInputStream fis = new FileInputStream(imagePath);
        byte[] pictureData = new byte[fis.available()];
        fis.read(pictureData);
        fis.close();
        return pictureData;
    }


}
