package nomasknohuman.spring.kafka.util;

// Utility class to read property file

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CollectorPropertyFileReader {
    private static final Logger logger = LogManager.getLogger(CollectorPropertyFileReader.class);

    private static Properties prop = new Properties();
    public static Properties readPropertyFile(String propertyFileName) throws Exception {
        if (prop.isEmpty()) {
            InputStream input = CollectorPropertyFileReader.class.getClassLoader().getResourceAsStream(propertyFileName); //"stream-collector.properties"
            try {
                prop.load(input);
            } catch (IOException e) {
                logger.error(e);
                throw e;
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        }
        return prop;
    }
}