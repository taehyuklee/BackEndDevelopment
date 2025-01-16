package restTemplate.usage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import restTemplate.usage.domain.dto.NormalityDto;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestTemplateService {

    public static RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(List.of((request, body, execution) -> {
            System.out.println("Request URI: " + request.getURI());
            System.out.println("Request Headers: " + request.getHeaders());
            System.out.println("Request Body: " + new String(body));
            return execution.execute(request, body);
        }));
        return restTemplate;
    }

    public <T> ResponseEntity<HashMap> postWithParamAndBody(T requestDto, String path){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8000")
                .path(String.format("/analysis/%s", path))
                .encode()
                .build()
                .toUri();

        RequestEntity<T> requestEntity = RequestEntity
                .post(uri)
                .header("importFormat", "json")
                .header("Content-Type", "application/json")
                .body(requestDto);

        RestTemplate restTemplate = createRestTemplate();

        ResponseEntity<HashMap> response = restTemplate.exchange(
                requestEntity, HashMap.class
        );

        System.out.println(response.getBody());

        return response;
    }

}

