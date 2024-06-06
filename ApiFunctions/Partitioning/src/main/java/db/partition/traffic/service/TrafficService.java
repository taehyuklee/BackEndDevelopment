package db.partition.traffic.service;

import db.partition.traffic.domain.dto.TrafficDto;
import db.partition.traffic.domain.entity.Traffic;
import db.partition.traffic.domain.repository.TrafficRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrafficService {

    private final TrafficRepository trafficRepository;

    public void insertTraffic(TrafficDto trafficDto){
        Traffic entity = new Traffic();
        BeanUtils.copyProperties(trafficDto, entity);

        trafficRepository.save(entity);

    }

}
