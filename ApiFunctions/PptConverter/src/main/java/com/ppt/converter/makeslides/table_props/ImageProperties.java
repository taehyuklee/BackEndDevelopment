package com.ppt.converter.makeslides.table_props;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ImageProperties {

    /* x, y, width, height */
    private Integer[] imagePosition;

    private String imgUrl;

    private String imgType;

    private Integer rowHeight;


}
