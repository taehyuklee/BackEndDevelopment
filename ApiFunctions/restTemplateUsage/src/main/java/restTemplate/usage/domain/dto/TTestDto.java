package restTemplate.usage.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class TTestDto {

    private List<Map<String, ?>> dataSet;

    private String grpFactor;

    private String ttestType;

    private Boolean equalVar;

    private String alternative;

    private Double refAvg;

}
