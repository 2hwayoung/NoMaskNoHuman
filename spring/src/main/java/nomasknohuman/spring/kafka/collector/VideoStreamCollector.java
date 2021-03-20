package nomasknohuman.spring.kafka.collector;

import nomasknohuman.spring.kafka.util.PropertyFileReader;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class VideoStreamCollector {

    private static final Logger logger = LogManager.getLogger(VideoStreamCollector.class);

//    public static void main(String[] args) throws Exception {
    public static void videoStreamCollector(String camera_id, String camera_url) throws Exception {
        // set producer configs
        Properties prop = PropertyFileReader.readPropertyFile("stream-collector.properties");
        Properties configs = new Properties();
        configs.put("bootstrap.servers", prop.getProperty("kafka.bootstrap.servers"));
        configs.put("acks", prop.getProperty("kafka.acks"));
        configs.put("retries",prop.getProperty("kafka.retries"));
        configs.put("batch.size", prop.getProperty("kafka.batch.size"));
        configs.put("linger.ms", prop.getProperty("kafka.linger.ms"));
        configs.put("max.request.size", prop.getProperty("kafka.max.request.size"));
        configs.put("compression.type", prop.getProperty("kafka.compression.type"));
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // generate event
        Producer<String, String> producer = new KafkaProducer<String, String>(configs);
//        generateIoTEvent(producer,prop.getProperty("kafka.topic"),prop.getProperty("camera.id"),prop.getProperty("camera.url"));
        generateIoTEvent(producer,prop.getProperty("kafka.topic"),camera_id, camera_url);
    }

    private static void generateIoTEvent(Producer<String, String> producer, String topic, String camId, String videoUrl) throws Exception {
        String[] urls = videoUrl.split(",");
        String[] ids = camId.split(",");
        if(urls.length != ids.length){
            throw new Exception("There should be same number of camera Id and url");
        }
        logger.info("Total urls to process "+urls.length);
        for(int i=0;i<urls.length;i++){
            Thread t = new Thread(new VideoEventGenerator(ids[i].trim(),urls[i].trim(),producer,topic));
            t.start();
        }
    }
}