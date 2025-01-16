package restTemplate.usage.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class LinearRegDto {

    private List<Map<String, ?>> dataSet;

    private Boolean analysisYn;

    private String grpFactor;

    private String targetGrp;

    private String regType;

    @JsonProperty("xValues")
    private List<String> xValues;

    @JsonProperty("yValue")
    private String yValue;

    private Boolean regPlotYn;
}
