package nomasknohuman.spring.repository;

import nomasknohuman.spring.domain.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

}
