package pl.tobynartowski;

import lombok.*;
import pl.tobynartowski.util.color.ColorPalette;

import java.util.Objects;

@Getter
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EyeContext {

    private int windowSize;
    private float frameRate;
    private float videoDuration;
    private float eyeSize;
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
