package pl.tobynartowski.configuration.mapper;

import pl.tobynartowski.component.iris.IrisReflectionType;
import pl.tobynartowski.configuration.property.ConfigurationProperties;
import pl.tobynartowski.configuration.property.ConfigurationProperty;

import static processing.core.PApplet.map;

public class FragilityMapper {

    // TODO: Add functionality to add multiple mappers to same value
    public static float getEyeSize(ConfigurationProperties properties) {
        return map(
                properties.getNumericProperty(ConfigurationProperty.FRAGILITY), 0, 100, 220f, 180f);
    }

    public static float getPupilSize(ConfigurationProperties properties) {
        return map(
                properties.getNumericProperty(ConfigurationProperty.FRAGILITY), 0, 100, 0.5f, 1f);
    }

    public static float getEyelidHeight(ConfigurationProperties properties) {
        return map(
                properties.getNumericProperty(ConfigurationProperty.FRAGILITY), 0, 100, 50f, 15f);
    }

    public static float getEyelashQuantity(ConfigurationProperties properties) {
        return map(properties.getNumericProperty(ConfigurationProperty.FRAGILITY), 0, 100, 8f, 22f);
    }

    public static float getEyelashWeight(ConfigurationProperties properties) {
        return map(
                properties.getNumericProperty(ConfigurationProperty.FRAGILITY), 0, 100, 10.5f, 3.5f);
    }

    public static float getColor(ConfigurationProperties properties) {
        return map(
                properties.getNumericProperty(ConfigurationProperty.FRAGILITY), 0, 100, 1.3f, 0.8f);
    }

    public static IrisReflectionType getReflectionType(ConfigurationProperties properties) {
        return properties.getNumericProperty(ConfigurationProperty.FRAGILITY) < 70
                ? IrisReflectionType.JELLY
                : IrisReflectionType.STAR;
    }
}
