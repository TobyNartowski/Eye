package pl.tobynartowski.util.color;

import lombok.Value;

@Value(staticConstructor = "of")
public class Color {

    float hue;
    float saturation;
    float brightness;
}
