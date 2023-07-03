package db.partition.partitioning.controller;

import db.partition.partitioning.service.PartitionApiService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partition")
public class PartitionController {

    private final PartitionApiService partitionService;

    @GetMapping("/traffic")
    public void createTable(){
        partitionService.createTraffic();
    }

    @DeleteMapping("/traffic")
    public void deleteTable(){
        partitionService.deleteTraffic();
    }

    @GetMapping("/monthly-new-partition")
    public void partition(){
        partitionService.partitionMonthlyTraffic();
    }

    @GetMapping("/monthly-droppart")
    public void drop(){
        partitionService.deleteMonthlyPartition();
    }

    @GetMapping("/daily-new-partition")
    public void partitionDaily(){
        partitionService.partitionDailyTraffic();
    }

    @GetMapping("/daily-droppart")
    public void dropDaily(){
        partitionService.deleteDailyPartition();
    }
}
