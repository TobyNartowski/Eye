package pl.tobynartowski.component.iris;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.tobynartowski.component.Renderable;
import pl.tobynartowski.utils.Color;
import processing.core.PApplet;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
class Iris implements Renderable {

    Color irisColor;
    float irisSize;
    float irisX = 0;
    float irisY = 0;

    Color pupilColor;
    float pupilSize;

    IrisReflectionType reflectionType;
    Color reflectionColor;
    float reflectionSize;
    float reflectionX = 0;
    float reflectionY = 0;

    private final Pupil pupil = new Pupil();
    private final Reflection reflection = new Reflection();

    @Override
    public void render(PApplet context) {
        context.fill(irisColor.getHue(), irisColor.getSaturation(), irisColor.getBrightness());
        context.ellipse(irisX, irisY, irisSize, irisSize);

        pupil.render(context);
        reflection.render(context);
    }

    private class Pupil implements Renderable {

        @Override
        public void render(PApplet context) {
            context.fill(
                    pupilColor.getHue(), pupilColor.getSaturation(), pupilColor.getBrightness());
            context.ellipse(irisX, irisY, pupilSize, pupilSize);
        }
    }

    private class Reflection implements Renderable {

        @Override
        public void render(PApplet context) {
            context.noStroke();
            context.fill(
                    reflectionColor.getHue(),
                    reflectionColor.getSaturation(),
                    reflectionColor.getBrightness());

            if (IrisReflectionType.STAR.equals(reflectionType)) {
                drawStar(context, reflectionX, reflectionY, reflectionSize, reflectionSize * 2);
            } else {
                drawJelly(
                        context,
                        reflectionX,
                        reflectionY,
                        reflectionSize * 3,
                        PApplet.radians(100));
            }
        }

        private void drawStar(PApplet context, float x, float y, float radius1, float radius2) {
            float angle = PApplet.TWO_PI / 5f;
            float halfAngle = angle / 2f;
            context.beginShape();

            for (float a = 0; a < PApplet.TWO_PI; a += angle) {
                float sx = x + PApplet.cos(a) * radius2;
                float sy = y + PApplet.sin(a) * radius2;
                context.vertex(sx, sy);
                sx = x + PApplet.cos(a + halfAngle) * radius1;
                sy = y + PApplet.sin(a + halfAngle) * radius1;
                context.vertex(sx, sy);
            }

            context.endShape(PApplet.CLOSE);
        }

        private void drawJelly(PApplet context, float x, float y, float size, float arcSize) {
            for (float angle = 0.1f; angle <= arcSize; angle += 0.1f) {
                float x1 = size * PApplet.cos(angle);
                float y1 = size * -PApplet.sin(angle);
                context.ellipse(x + x1, y + y1, 8, 8);
            }
        }
    }
}
