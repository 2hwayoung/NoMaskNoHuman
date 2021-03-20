package nomasknohuman.spring.controller;

import lombok.AllArgsConstructor;
import nomasknohuman.spring.dto.VideoDto;
import nomasknohuman.spring.service.S3Service;
import nomasknohuman.spring.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static nomasknohuman.spring.kafka.collector.VideoStreamCollector.videoStreamCollector;
import static nomasknohuman.spring.kafka.consumer.VideoStreamConsumer.videoStreamConsumer;

@Controller // 웹 MVC의 컨트롤러 역할
@AllArgsConstructor // 클래스에 존재하는 모든 필드에 대한 생성자를 자동 생성
public class VideoController {
    private S3Service s3Service;
    private VideoService videoService;

    @GetMapping("/video")
    public String dispWrite() {
        return "/video";
    }

    @PostMapping("/video")
    public String execWrite(VideoDto videoDto, MultipartFile file) throws Exception {
        String imgPath = s3Service.upload(file);
        videoDto.setFilePath(imgPath);

        videoService.savePost(videoDto);

        videoStreamCollector("offline-video", imgPath);

        return "redirect:/video";
    }

    @GetMapping("/live")
    @ResponseBody
    public String live() throws Exception {

        videoStreamCollector("realtime-video", "http://172.24.94.176:8080/video");

        return "/liveoutput";
    }

    @GetMapping("/liveoutput")
    @ResponseBody
    public String liveoutput() throws Exception {

        videoStreamConsumer();

        return "redirect:/video";
    }

    @RestController
    public class TestController {
        @GetMapping("/api/hello")
        public String hello(){
            return "안녕하세요. 현재 서버시간은 "+new Date() +"입니다. \n";
        }
    }
}
