package pl.tobynartowski;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import pl.tobynartowski.mapper.Mappable;
import pl.tobynartowski.mapper.MappedValue;
import pl.tobynartowski.util.color.ColorPalette;

import java.util.Objects;

@Mappable
@Getter
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants
public class EyeContext {

    // Rigged values
    @MappedValue(min = 180f, max = 220f)
    private float eyeSize;

    @MappedValue(min = 0.8f, max = 1.3f)
    private float colorIntensifier;
    // Rigged values end

    private int windowSize;
    private float frameRate;
    private float videoDuration;
    private ColorPalette colorPalette;

    @Setter(AccessLevel.PACKAGE)
    private static EyeContext instance;

    public static EyeContext getInstance() {
        if (Objects.isNull(instance)) {
            throw new IllegalStateException("Application is not configured.");
        }

        return instance;
    }
}
