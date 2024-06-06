package db.partition.traffic.controller;

import db.partition.traffic.domain.dto.TrafficDto;
import db.partition.traffic.service.TrafficService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TrafficController {

    private final TrafficService trafficService;

    @PostMapping("/traffic")
    public void createTable(@RequestBody TrafficDto trafficDto){
        trafficService.insertTraffic(trafficDto);
    }

}
