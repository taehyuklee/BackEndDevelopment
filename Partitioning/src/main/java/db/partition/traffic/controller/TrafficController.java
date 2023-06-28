package db.partition.traffic.controller;

import db.partition.partitioning.PartitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partition")
public class TrafficController {

    private final PartitionService partitionService;

    @GetMapping("/traffic")
    public void createTable(){
        partitionService.createTraffic();
    }

}
