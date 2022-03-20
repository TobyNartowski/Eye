package pl.tobynartowski.utils.color;

import lombok.Value;

@Value(staticConstructor = "of")
public class Color {

    float hue;
    float saturation;
    float brightness;
}
