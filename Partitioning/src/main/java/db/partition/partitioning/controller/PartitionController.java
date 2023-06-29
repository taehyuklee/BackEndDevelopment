package db.partition.partitioning.controller;

import db.partition.partitioning.service.PartitionService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partition")
public class PartitionController {

    private final PartitionService partitionService;

    @GetMapping("/traffic")
    public void createTable(){
        partitionService.createTraffic();
    }

    @DeleteMapping("/traffic")
    public void deleteTable(){
        partitionService.deleteTraffic();
    }

    @GetMapping("/newpartition")
    public void partition(){
        partitionService.partitionTraffic();
    }

    @GetMapping("/droppart")
    public void drop(){
        partitionService.deletePartition();
    }
}
