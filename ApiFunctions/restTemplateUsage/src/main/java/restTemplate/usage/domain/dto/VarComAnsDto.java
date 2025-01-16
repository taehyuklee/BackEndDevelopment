package restTemplate.usage.domain.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class VarComAnsDto {

    private List<Map<String, ?>> dataSet;

    private List<String> treatments;

    private String targetCol;

    private Boolean interactionYn;

    private Boolean randomYn;

    private Boolean convCheckYn;
}
