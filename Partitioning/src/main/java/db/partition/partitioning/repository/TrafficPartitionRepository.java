package db.partition.partitioning.repository;

import db.partition.traffic.domain.entity.Traffic;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TrafficPartitionRepository extends CrudRepository<Traffic, Long> {

    @Modifying
    @Query(value = "create table public.\"TRAFFIC\" (\"ID\" serial not NULL , \"START_TIME\" timeStamp, \"END_TIME\" timeStamp, \"CONTENTS\" varchar(50), PRIMARY KEY (\"ID\", \"END_TIME\")) partition by range (\"END_TIME\")", nativeQuery = true)
    void createTrafficTable();

    @Modifying
    @Query(value = "create table public.\"TRAFFIC_partition\"  partition of public.\"TRAFFIC\" for values from ?1 to ?2", nativeQuery = true)
    void createPartition();
}
