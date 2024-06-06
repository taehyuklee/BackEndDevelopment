package db.partition.traffic.domain.repository;

import db.partition.traffic.domain.entity.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficRepository extends JpaRepository<Traffic, Long> {

}
