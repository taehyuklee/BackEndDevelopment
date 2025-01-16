package restTemplate.usage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import restTemplate.usage.domain.dto.*;
import restTemplate.usage.service.RestTemplateService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class RestTemplateController {

    private final RestTemplateService restTemplateService;

    @PostMapping("/normality-test")
    public HashMap restTemplateNormTest(@RequestBody NormalityDto normalityDto){

        ResponseEntity<HashMap> response =  restTemplateService.postWithParamAndBody(normalityDto, "normality-test");

        System.out.println(response.getBody());

        return response.getBody();
    }

    @PostMapping("/t-test")
    public HashMap restTemplateTTest(@RequestBody TTestDto tTestDto){

        ResponseEntity<HashMap> response =  restTemplateService.postWithParamAndBody(tTestDto, "t-test");

        System.out.println(response.getBody());

        return response.getBody();
    }

    @PostMapping("/cor-analysis")
    public HashMap restTemplateCorAns(@RequestBody CorAnsDto corAnsDto){

        ResponseEntity<HashMap> response =  restTemplateService.postWithParamAndBody(corAnsDto, "cor-analysis");

        System.out.println(response.getBody());

        return response.getBody();
    }

    @PostMapping("/var-test")
    public HashMap getRestTemplateVarTest(@RequestBody VarTestDto varTestDto){

        ResponseEntity<HashMap> response =  restTemplateService.postWithParamAndBody(varTestDto, "var-test");

        System.out.println(response.getBody());

        return response.getBody();
    }

    @PostMapping("/one-way-anova")
    public HashMap getRestTemplateAnova(@RequestBody OneWayAnovaDto oneWayAnovaDto){

        ResponseEntity<HashMap> response =  restTemplateService.postWithParamAndBody(oneWayAnovaDto, "one-way-anova");

        System.out.println(response.getBody());

        return response.getBody();
    }

    @PostMapping("/linear-reg")
    public HashMap getRestTemplateLinearReg(@RequestBody LinearRegDto linearRegDto){

        ResponseEntity<HashMap> response =  restTemplateService.postWithParamAndBody(linearRegDto, "linear-reg");

        System.out.println(response.getBody());

        return response.getBody();
    }


    @PostMapping("/detect-outlier")
    public HashMap getRestTemplateDetectionOfOultiers(@RequestBody DetectionOfOutliers detectionOfOutliers){

        ResponseEntity<HashMap> response =  restTemplateService.postWithParamAndBody(detectionOfOutliers, "detect-outlier");

        System.out.println(response.getBody());

        return response.getBody();
    }


    @PostMapping("/confidence-interval")
    public HashMap getRestTemplateConfInterval(@RequestBody ConfidenceIntervalDto confidenceIntervalDto){

        ResponseEntity<HashMap> response =  restTemplateService.postWithParamAndBody(confidenceIntervalDto, "confidence-interval");

        System.out.println(response.getBody());

        return response.getBody();
    }


    @PostMapping("/var-com-analysis")
    public HashMap getRestTemplateVarComAns(@RequestBody VarComAnsDto varComAnsDto){

        ResponseEntity<HashMap> response =  restTemplateService.postWithParamAndBody(varComAnsDto, "var-com-analysis");

        System.out.println(response.getBody());

        return response.getBody();
    }


    @PostMapping("/avg-run-length")
    public HashMap getRestTemplateVarComAns(@RequestBody AvgRunLengthDto avgRunLengthDto){

        ResponseEntity<HashMap> response =  restTemplateService.postWithParamAndBody(avgRunLengthDto, "avg-run-length");

        System.out.println(response.getBody());

        return response.getBody();
    }


}
