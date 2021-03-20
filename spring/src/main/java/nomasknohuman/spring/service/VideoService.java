package nomasknohuman.spring.service;

import lombok.AllArgsConstructor;
import nomasknohuman.spring.repository.VideoRepository;
import nomasknohuman.spring.dto.VideoDto;
import org.springframework.stereotype.Service;

@Service // 핵심 비즈니스 로직 구현
@AllArgsConstructor
public class VideoService {
    private VideoRepository videoRepository;

    public void savePost(VideoDto videoDto) {
        videoRepository.save(videoDto.toEntity());
    }
}
