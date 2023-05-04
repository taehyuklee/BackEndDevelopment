package download.excel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import download.excel.domain.entity.ListEntity;

@Repository
public interface ExcelRepository extends MongoRepository<ListEntity, String>{

    List<ListEntity> findByGampangE(String string);
    
}
