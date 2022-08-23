package bulletinBoard.notice.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import bulletinBoard.notice.domain.entity.NoticeMultipartFile;


@Repository
public interface BulletinMultipartRepository extends MongoRepository<NoticeMultipartFile, String> {

    NoticeMultipartFile findByTitle(String title);

}

