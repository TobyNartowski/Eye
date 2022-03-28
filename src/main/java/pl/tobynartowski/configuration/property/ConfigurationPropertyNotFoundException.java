package pl.tobynartowski.configuration.property;

public class ConfigurationPropertyNotFoundException extends RuntimeException {

    public ConfigurationPropertyNotFoundException(ConfigurationProperty property) {
        super(String.format("Property %s not found", property.name().toLowerCase()));
    }
}
