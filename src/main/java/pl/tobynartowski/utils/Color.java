package pl.tobynartowski.utils;

import lombok.Value;

@Value(staticConstructor = "of")
public class Color {

    float hue;
    float saturation;
    float brightness;
}
