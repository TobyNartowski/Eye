package pl.tobynartowski.configuration.property;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.reflections.Reflections;
import pl.tobynartowski.EyeApplication;
import pl.tobynartowski.mapper.Mappable;
import pl.tobynartowski.mapper.MappableFieldNotFound;
import pl.tobynartowski.mapper.MappedValue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class ConfigurationPropertyMapper {

    private static final float BOOLEAN_CLASSIFICATION_THRESHOLD = 50.0f;
    private static final Set<Class<?>> mappableClasses;

    static {
        Reflections reflections = new Reflections(EyeApplication.class.getPackage().getName());
        mappableClasses = reflections.getTypesAnnotatedWith(Mappable.class);
    }

    public static boolean getBooleanMappedValue(String field) {
        return getFloatMappedValue(field) >= BOOLEAN_CLASSIFICATION_THRESHOLD;
    }

    public static int getIntegerMappedValue(String field) {
        return Math.round(getFloatMappedValue(field));
    }

    public static float getFloatMappedValue(String field) {
        MappedValue annotation = getFieldAnnotation(field);
        List<ConfigurationPropertyValueMapper> mappers = getConfigurationProperties(field);

        return calculateMean(field, annotation, mappers);
    }

    private static float calculateMean(
            String field, MappedValue annotation, List<ConfigurationPropertyValueMapper> mappers) {
        float originValue = getOriginValue(field);
        return (float)
                mappers.stream()
                        .map(mapper -> mapSingleMean(annotation, mapper, originValue))
                        .mapToDouble(v -> v)
                        .average()
                        .orElseThrow(
                                () -> new IllegalStateException("calculateMean() - stream error"));
    }

    private static float getOriginValue(String field) {
        return (float)
                Stream.of(ConfigurationProperty.values())
                        .filter(
                                property ->
                                        configurationPropertyInfluenceField(
                                                property.getInfluencedFields(), field))
                        .map(ConfigurationPropertyMapper::getConfigurationValue)
                        .mapToDouble(v -> v)
                        .average()
                        .orElseThrow(
                                () ->
                                        new IllegalStateException(
                                                "calculateOriginValue() - stream error"));
    }

    private static boolean configurationPropertyInfluenceField(
            List<ConfigurationPropertyValueMapper> influencedFields, String field) {
        return Objects.nonNull(influencedFields)
                && influencedFields.stream()
                        .map(ConfigurationPropertyValueMapper::getField)
                        .collect(Collectors.toList())
                        .contains(field);
    }

    private static float getConfigurationValue(ConfigurationProperty configurationProperty) {
        return (float) ConfigurationProperties.getNumericProperty(configurationProperty);
    }

    private static float mapSingleMean(
            MappedValue annotation, ConfigurationPropertyValueMapper mapper, float value) {
        return (value - mapper.getMin())
                        / (mapper.getMax() - mapper.getMin())
                        * (annotation.max() - annotation.min())
                + annotation.min();
    }

    private static List<ConfigurationPropertyValueMapper> getConfigurationProperties(String field) {
        return Stream.of(ConfigurationProperty.values())
                .flatMap(property -> property.getInfluencedFields().stream())
                .filter(mapper -> field.equals(mapper.getField()))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private static MappedValue getFieldAnnotation(String field) {
        Field targetField =
                mappableClasses.stream()
                        .filter(clazz -> classHasField(clazz, field))
                        .findAny()
                        .orElseThrow(() -> new MappableFieldNotFound(field))
                        .getDeclaredField(field);

        return targetField.getAnnotation(MappedValue.class);
    }

    private static boolean classHasField(Class<?> clazz, String field) {
        return Stream.of(clazz.getDeclaredFields()).anyMatch(f -> field.equals(f.getName()));
    }
}
