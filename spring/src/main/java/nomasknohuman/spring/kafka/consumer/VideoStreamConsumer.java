package nomasknohuman.spring.kafka.consumer;

import nomasknohuman.spring.kafka.collector.VideoStreamCollector;
import nomasknohuman.spring.kafka.util.PropertyFileReader;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class VideoStreamConsumer {

    private static final Logger logger = LogManager.getLogger(VideoStreamCollector.class);

    public static void videoStreamConsumer() throws Exception {
        Properties prop = PropertyFileReader.readPropertyFile("stream-consumer.properties");
        Properties configs = new Properties();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, prop.getProperty("kafka.bootstrap.servers"));
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, prop.getProperty("kafka.group"));
//        configs.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, prop.getProperty("kafka.max.partition.fetch.bytes"));
//        configs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, prop.getProperty("kafka.max.poll.records"));
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(Arrays.asList(prop.getProperty("kafka.topic")));
        logger.info("subscribe success");
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            logger.info("success", records.count());
            for (ConsumerRecord<String, String> record : records) {
                logger.info("record-value: ", record.value());
                consumer.commitSync();
                record.offset();
            }
        }
    }

}
