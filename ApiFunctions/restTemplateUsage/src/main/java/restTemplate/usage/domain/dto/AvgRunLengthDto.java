package restTemplate.usage.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class AvgRunLengthDto {

    private List<Map<String, ?>> dataSet;

    private Double type1Err;

    private String targetNm;

    private Double targetVal;

    private Double criteria;
}
