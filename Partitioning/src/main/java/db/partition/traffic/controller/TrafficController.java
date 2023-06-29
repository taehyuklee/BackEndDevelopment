package db.partition.traffic.controller;

import db.partition.partitioning.service.PartitionService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TrafficController {

    private final PartitionService partitionService;

    @PostMapping("/traffic")
    public void createTable(){
        partitionService.createTraffic();
    }

    @DeleteMapping("/traffic")
    public void deleteTable(){
        partitionService.deleteTraffic();
    }

    @GetMapping("/traffic")
    public void partition(){
        partitionService.partitionTraffic();
    }

    @PutMapping("/traffic")
    public void drop(){
        partitionService.deletePartition();
    }
}
