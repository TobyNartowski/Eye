package pl.tobynartowski.configuration.property;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

public class ConfigurationProperties {

    private static final String CONFIG_FILENAME = "config.properties";
    private static final int PROPERTY_MIN_VALUE = 0;
    private static final int PROPERTY_MAX_VALUE = 100;

    private static final Properties properties = loadProperties();

    private static Properties loadProperties() {
        String rootPath =
                Optional.ofNullable(Thread.currentThread().getContextClassLoader().getResource(""))
                        .map(URL::getPath)
                        .orElseThrow(
                                () ->
                                        new ConfigurationFileException(
                                                "Resource directory couldn't be found"));
        String configPath = rootPath + CONFIG_FILENAME;

        var properties = new Properties();
        try {
            properties.load(new FileInputStream(configPath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConfigurationFileException(
                    "Cannot load configuration file: " + e.getMessage());
        }

        return properties;
    }

    public static int getNumericProperty(ConfigurationProperty property) {
        return Optional.ofNullable(properties.getProperty(property.name().toLowerCase()))
                .map(Integer::parseInt)
                .map(ConfigurationProperties::trimValueToRange)
                .orElseThrow(() -> new ConfigurationPropertyNotFoundException(property));
    }

    private static int trimValueToRange(int original) {
        if (original > PROPERTY_MAX_VALUE) {
            return PROPERTY_MAX_VALUE;
        } else {
            return Math.max(original, PROPERTY_MIN_VALUE);
        }
    }
}
