package com.ppt.converter.makeslides.functions;

import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTableRow;

public class MapTable {

    public void makeTable(XSLFSlide slide, String[][] data){

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

    public void recursiveCheck( XSLFTableCell cell, int m, XSLFTableRow row){
        System.out.println("++++" + m);
        if(m>=row.getCells().size()) {
            return;
        }
        if (cell.isMerged() || cell.getText() != null && !cell.getText().isEmpty()) {
            if(m<row.getCells().size()) m++;

            recursiveCheck(cell, m, row);

        }
    }
}
