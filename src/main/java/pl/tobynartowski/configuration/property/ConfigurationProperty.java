package pl.tobynartowski.configuration.property;

import lombok.Getter;
import pl.tobynartowski.EyeContext;
import pl.tobynartowski.component.eyelid.Eyelid;
import pl.tobynartowski.component.eyelid.EyelidAnimated;
import pl.tobynartowski.component.iris.Iris;
import pl.tobynartowski.component.iris.IrisAnimated;

import java.util.List;

public enum ConfigurationProperty {

    FRAGILITY(
            new ConfigurationPropertyValueMapper(EyeContext.Fields.eyeSize, ConfigurationPropertyValueMapper.Mode.INVERTED),
            new ConfigurationPropertyValueMapper(EyeContext.Fields.colorIntensifier, ConfigurationPropertyValueMapper.Mode.INVERTED),
            new ConfigurationPropertyValueMapper(Eyelid.Fields.eyelidHeight, ConfigurationPropertyValueMapper.Mode.INVERTED),
            new ConfigurationPropertyValueMapper(Eyelid.Fields.eyelashQuantity),
            new ConfigurationPropertyValueMapper(Eyelid.Fields.eyelashWeight, ConfigurationPropertyValueMapper.Mode.INVERTED),
            new ConfigurationPropertyValueMapper(Iris.Fields.pupilSize),
            new ConfigurationPropertyValueMapper(Iris.Fields.reflectionType)),
    NERVOUSNESS(
            new ConfigurationPropertyValueMapper(IrisAnimated.Fields.gazeFrequency, ConfigurationPropertyValueMapper.Mode.INVERTED),
            new ConfigurationPropertyValueMapper(IrisAnimated.Fields.gazeSmallMoveStrength),
            new ConfigurationPropertyValueMapper(IrisAnimated.Fields.gazeSmallMoveFrequency, ConfigurationPropertyValueMapper.Mode.INVERTED),
            new ConfigurationPropertyValueMapper(IrisAnimated.Fields.gazeSpeed),
            new ConfigurationPropertyValueMapper(IrisAnimated.Fields.gazeNervousness),
            new ConfigurationPropertyValueMapper(EyelidAnimated.Fields.blinkingFrequency),
            new ConfigurationPropertyValueMapper(EyelidAnimated.Fields.blinkingSpeed)
    ),
    HAPPINESS(
            new ConfigurationPropertyValueMapper(IrisAnimated.Fields.gazePride),
            new ConfigurationPropertyValueMapper(IrisAnimated.Fields.gazePrideFrequency)
    );

    @Getter private final List<ConfigurationPropertyValueMapper> influencedFields;

    ConfigurationProperty(ConfigurationPropertyValueMapper... mappers) {
        this.influencedFields = List.of(mappers);
    }
}
