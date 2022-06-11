package pl.tobynartowski.configuration.property;

import lombok.Getter;
import pl.tobynartowski.EyeContext;
import pl.tobynartowski.component.eyelid.Eyelid;
import pl.tobynartowski.component.iris.Iris;

import java.util.List;

public enum ConfigurationProperty {

    FRAGILITY(
            new ConfigurationPropertyValueMapper(EyeContext.Fields.eyeSize, ConfigurationPropertyValueMapper.Mode.INVERTED),
            new ConfigurationPropertyValueMapper(EyeContext.Fields.colorIntensifier, ConfigurationPropertyValueMapper.Mode.INVERTED),
            new ConfigurationPropertyValueMapper(Eyelid.Fields.eyelidHeight, ConfigurationPropertyValueMapper.Mode.INVERTED),
            new ConfigurationPropertyValueMapper(Eyelid.Fields.eyelashQuantity),
            new ConfigurationPropertyValueMapper(Eyelid.Fields.eyelashWeight, ConfigurationPropertyValueMapper.Mode.INVERTED),
            new ConfigurationPropertyValueMapper(Iris.Fields.pupilSize),
            new ConfigurationPropertyValueMapper(Iris.Fields.reflectionType));

    @Getter private final List<ConfigurationPropertyValueMapper> influencedFields;

    ConfigurationProperty(ConfigurationPropertyValueMapper... mappers) {
        this.influencedFields = List.of(mappers);
    }
}
