package nomasknohuman.spring.dto;

import lombok.*;
import nomasknohuman.spring.domain.VideoEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class VideoDto { // 데이터 전송 객체
    private Long id;
    private String title;
    private String filePath;

    public VideoEntity toEntity(){
        VideoEntity build = VideoEntity.builder()
                .id(id)
                .title(title)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public VideoDto(Long id, String title, String filePath) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
    }

    public String getTitle() {
        return this.title;
    }
}