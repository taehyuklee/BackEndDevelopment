package restTemplate.usage.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class VarTestDto {

    private List<Map<String, ?>> dataSet;

    private String grpFactor;

    private Boolean normalityYn;

    private String alternative;

    private Double refVar;

}
