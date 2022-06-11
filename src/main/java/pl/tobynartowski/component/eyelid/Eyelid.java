package pl.tobynartowski.component.eyelid;

import lombok.experimental.FieldNameConstants;
import pl.tobynartowski.EyeContext;
import pl.tobynartowski.component.Renderable;
import pl.tobynartowski.mapper.Mappable;
import pl.tobynartowski.mapper.MappedValue;
import pl.tobynartowski.util.color.Color;
import processing.core.PApplet;

@Mappable
@FieldNameConstants
public class Eyelid implements Renderable {

    protected final EyeContext configuration = EyeContext.getInstance();
    private final float eyelidMaskOffset = 5;

    // Rigged values
    @MappedValue(min = 15, max = 50)
    float eyelidHeight;
    Color eyelashColor;

    @MappedValue(min = 8, max = 15)
    int eyelashQuantity;

    @MappedValue(min = 5.5f, max = 10.5f)
    float eyelashWeight;

    Color upperEyelidColor;
    Color lowerEyelidColor;
    // Rigged values end

    float eyelashDeformation;
    float upperEyelidDeformation;
    float lowerEyelidDeformation;

    private final Eyelash eyelash = new Eyelash();
    private final UpperEyelid upperEyelid = new UpperEyelid();
    private final LowerEyelid lowerEyelid = new LowerEyelid();
    private final UpperEyelidMask upperEyelidMask = new UpperEyelidMask();
    private final LowerEyelidMask lowerEyelidMask = new LowerEyelidMask();

    @Override
    public void render(PApplet context) {
        lowerEyelidMask.render(context);
        upperEyelidMask.render(context);

        lowerEyelid.render(context);
        upperEyelid.render(context);

        eyelash.render(context);
    }

    private class Eyelash implements Renderable {

        @Override
        public void render(PApplet context) {
            context.noFill();
            context.bezier(
                    -configuration.getEyeSize(),
                    -eyelidHeight,
                    -(configuration.getEyeSize() / 2),
                    eyelashDeformation,
                    (configuration.getEyeSize() / 2),
                    eyelashDeformation,
                    configuration.getEyeSize(),
                    -eyelidHeight);

            for (int i = 0; i <= eyelashQuantity; i++) {
                float t = i / (float) eyelashQuantity;
                float x =
                        context.bezierPoint(
                                -configuration.getEyeSize(),
                                -(configuration.getEyeSize() / 2),
                                (configuration.getEyeSize() / 2),
                                configuration.getEyeSize(),
                                t);
                float y =
                        context.bezierPoint(
                                -eyelidHeight,
                                eyelashDeformation,
                                eyelashDeformation,
                                -eyelidHeight,
                                t);
                float tx =
                        context.bezierTangent(
                                -configuration.getEyeSize(),
                                -(configuration.getEyeSize() / 2),
                                (configuration.getEyeSize() / 2),
                                configuration.getEyeSize(),
                                t);
                float ty =
                        context.bezierTangent(
                                -eyelidHeight,
                                eyelashDeformation,
                                eyelashDeformation,
                                -eyelidHeight,
                                t);

                context.strokeWeight(eyelashWeight);
                context.stroke(
                        eyelashColor.getHue(),
                        eyelashColor.getSaturation(),
                        eyelashColor.getBrightness());

                float angle = (float) (Math.atan2(ty, tx) - 1.2);
                float eyelashWidth =
                        PApplet.map(Math.abs(x), configuration.getEyeSize(), 0, 0, 1.5f) + 0.5f;

                context.line(
                        x,
                        y,
                        PApplet.cos(angle) * (18 * eyelashWidth) + x,
                        PApplet.sin(angle) * (12 * eyelashWidth) + y);
                context.noStroke();
            }
        }
    }

    private class UpperEyelid implements Renderable {

        @Override
        public void render(PApplet context) {
            context.fill(
                    upperEyelidColor.getHue(),
                    upperEyelidColor.getSaturation(),
                    upperEyelidColor.getBrightness());
            context.beginShape();
            context.vertex(-configuration.getEyeSize(), 0);
            context.bezierVertex(
                    -(configuration.getEyeSize() / 2),
                    upperEyelidDeformation,
                    (configuration.getEyeSize() / 2),
                    upperEyelidDeformation,
                    configuration.getEyeSize(),
                    0);
            context.bezierVertex(
                    (configuration.getEyeSize() / 2),
                    upperEyelidDeformation - eyelidHeight,
                    -(configuration.getEyeSize() / 2),
                    upperEyelidDeformation - eyelidHeight,
                    -configuration.getEyeSize(),
                    0);
            context.endShape();
        }
    }

    private class LowerEyelid implements Renderable {

        @Override
        public void render(PApplet context) {
            context.fill(
                    lowerEyelidColor.getHue(),
                    lowerEyelidColor.getSaturation(),
                    lowerEyelidColor.getBrightness());
            context.beginShape();
            context.vertex(-configuration.getEyeSize(), 0);
            context.bezierVertex(
                    -(configuration.getEyeSize() / 2),
                    lowerEyelidDeformation,
                    (configuration.getEyeSize() / 2),
                    lowerEyelidDeformation,
                    configuration.getEyeSize(),
                    0);
            context.bezierVertex(
                    (configuration.getEyeSize() / 2),
                    lowerEyelidDeformation + eyelidHeight,
                    -(configuration.getEyeSize() / 2),
                    lowerEyelidDeformation + eyelidHeight,
                    -configuration.getEyeSize(),
                    0);
            context.endShape();
        }
    }

    private class UpperEyelidMask implements Renderable {

        @Override
        public void render(PApplet context) {
            context.fill(
                    configuration.getColorPalette().getBackgroundColor().getHue(),
                    configuration.getColorPalette().getBackgroundColor().getSaturation(),
                    configuration.getColorPalette().getBackgroundColor().getBrightness());
            context.beginShape();
            context.vertex(-configuration.getEyeSize(), 0);
            context.bezierVertex(
                    -(configuration.getEyeSize() / 2),
                    upperEyelidDeformation - eyelidMaskOffset,
                    (configuration.getEyeSize() / 2),
                    upperEyelidDeformation - eyelidMaskOffset,
                    configuration.getEyeSize(),
                    0);
            context.vertex(configuration.getEyeSize(), -configuration.getEyeSize());
            context.vertex(-configuration.getEyeSize(), -configuration.getEyeSize());
            context.vertex(-configuration.getEyeSize(), 0);
            context.endShape();
        }
    }

    private class LowerEyelidMask implements Renderable {

        @Override
        public void render(PApplet context) {
            context.fill(
                    configuration.getColorPalette().getBackgroundColor().getHue(),
                    configuration.getColorPalette().getBackgroundColor().getSaturation(),
                    configuration.getColorPalette().getBackgroundColor().getBrightness());
            context.beginShape();
            context.vertex(-configuration.getEyeSize(), 0);
            context.bezierVertex(
                    -(configuration.getEyeSize() / 2),
                    lowerEyelidDeformation + eyelidMaskOffset,
                    (configuration.getEyeSize() / 2),
                    lowerEyelidDeformation + eyelidMaskOffset,
                    configuration.getEyeSize(),
                    0);
            context.vertex(configuration.getEyeSize(), configuration.getEyeSize());
            context.vertex(-configuration.getEyeSize(), configuration.getEyeSize());
            context.vertex(-configuration.getEyeSize(), 0);
            context.endShape();
        }
    }
}
