package restTemplate.usage.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class CorAnsDto {

    private List<Map<String, ?>> dataSet;

    private String pValueYn;

    private String heatMapYn;

    private Boolean scatterPlotYn;


}
