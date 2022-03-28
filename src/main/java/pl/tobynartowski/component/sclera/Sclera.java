package pl.tobynartowski.component.sclera;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.tobynartowski.component.Renderable;
import pl.tobynartowski.util.color.Color;
import processing.core.PApplet;
import processing.core.PConstants;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Sclera implements Renderable {

    Color color;
    float width;
    float height;

    @Override
    public void render(PApplet context) {
        context.fill(color.getHue(), color.getSaturation(), color.getBrightness());

        context.rectMode(PConstants.CENTER);
        context.rect(0, 0, width, height);
    }
}
