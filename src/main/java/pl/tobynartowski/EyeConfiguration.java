package pl.tobynartowski;

import lombok.*;
import pl.tobynartowski.utils.color.Color;
import pl.tobynartowski.utils.color.ColorPalette;

import java.util.Objects;

@Getter
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EyeConfiguration {

    private int windowSize;
    private float frameRate;
    private float eyeSize;
    private ColorPalette colorPalette;

    @Setter(AccessLevel.PACKAGE)
    private static EyeConfiguration instance;

    public static EyeConfiguration getInstance() {
        if (Objects.isNull(instance)) {
            throw new IllegalStateException("Application is not configured.");
        }

        return instance;
    }
}
