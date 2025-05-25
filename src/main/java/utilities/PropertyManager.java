package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static final Properties properties = new Properties();
    private static final Logger logger = LogManager.getLogger(PropertyManager.class);

    static {
        try (InputStream input = PropertyManager.class.getClassLoader()
                .getResourceAsStream("testng.properties")) {
            if (input == null) {
                throw new FileNotFoundException("testng.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException ex) {
            logger.error("Error loading properties file", ex);
            throw new RuntimeException("Failed to load properties file", ex);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in configuration", key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    public static long getLongProperty(String key) {
        return Long.parseLong(getProperty(key));
    }
}