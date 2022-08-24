package bulletinBoard.notice.domain.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import bulletinBoard.notice.domain.entity.Notice;

@Repository
public interface BulletinRepository extends MongoRepository<Notice, String>{

    Notice findByTitle(String title);
    Page<Notice> findByTitleIgnoreCaseContaining(String title, Pageable pageable);
    Page<Notice> findByContentIgnoreCaseContaining(String content, Pageable pageable);
    Page<Notice> findAll(Pageable pageable);
    
}
