package pl.tobynartowski.configuration.property;

import lombok.Data;

@Data
public class ConfigurationPropertyValueMapper {

    private final String field;
    private final float min;
    private final float max;

    ConfigurationPropertyValueMapper(String field) {
        this.field = field;
        this.min = 0;
        this.max = 100;
    }

    ConfigurationPropertyValueMapper(String field, Mode mapperMode) {
        this.field = field;

        switch (mapperMode) {
            case DEFAULT:
                this.min = 0;
                this.max = 100;
                break;
            case INVERTED:
                this.min = 100;
                this.max = 0;
                break;
            default:
                throw new IllegalStateException("No PropertyMapperMode value found: " + mapperMode);
        }
    }

    enum Mode {
        DEFAULT, INVERTED;
    }
}
