package com.ppt.converter.makeslides.table_props;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class TableProperties {

    private Integer numColumns;

    private Integer numRows;

    /* 예시 : 50 , 70 */
    private Integer colWidth;

    private Boolean headerYn;

    /* Header의 Height 예시: 50 */
    private Double headerHeight;

    private Double gridHeight;

    private String[] headersNm;

    private Integer borderThickness;

    /* x, y, width, height */
    private Integer[] tablePosition;

    /* R, G, B 순서대로 */
    private Integer[] fillColor;

    /* R, G, B 순서대로 */
    private Integer[] borderColor;

    private Boolean centerAlign;

    private Double fontSize;

    private Boolean bold;


}
