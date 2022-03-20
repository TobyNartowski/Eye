package pl.tobynartowski.component;

import processing.core.PApplet;

@FunctionalInterface
public interface Renderable {

    void render(PApplet context);
}
