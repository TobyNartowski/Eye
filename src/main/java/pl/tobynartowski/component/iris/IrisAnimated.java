package pl.tobynartowski.component.iris;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import pl.tobynartowski.EyeContext;
import pl.tobynartowski.mapper.Mappable;
import pl.tobynartowski.mapper.MappedValue;
import pl.tobynartowski.util.color.Color;
import processing.core.PApplet;

@Mappable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants
public class IrisAnimated extends Iris {

    // Rigged values
    @MappedValue(min = 50f, max = 200f)
    private float gazeFrequency;

    @MappedValue(min = 0f, max = 0.10f)
    private float gazeSmallMoveStrength;

    @MappedValue(min = 1f, max = 6f)
    private int gazeSmallMoveFrequency;

    @MappedValue(min = 0.08f, max = 0.25f)
    private float gazeSpeed;

    @MappedValue(min = 1f, max = 6f)
    private int gazePrideFrequency;

    @MappedValue
    private boolean gazePride;

    @MappedValue(min = 2f, max = 4f)
    private float gazeNervousness;
    // Rigged values end

    private final EyeContext eyeContext = EyeContext.getInstance();

    private float xTarget = 0f;
    private float yTarget = 0f;
    private float xLerp = 0f;
    private float yLerp = 0f;
    private float wiggle = 0f;

    private int gazeBuffer = 1;

    @Builder
    public IrisAnimated(
            Color irisColor,
            float irisSize,
            Color pupilColor,
            float pupilSize,
            int reflectionType,
            Color reflectionColor,
            float reflectionSize,
            float gazeFrequency,
            float gazeSmallMoveStrength,
            int gazeSmallMoveFrequency,
            float gazeSpeed,
            int gazePrideFrequency,
            boolean gazePride,
            float gazeNervousness) {
        this.irisColor = irisColor;
        this.irisSize = irisSize;
        this.pupilColor = pupilColor;
        this.pupilSize = pupilSize;
        this.reflectionType = reflectionType;
        this.reflectionColor = reflectionColor;
        this.reflectionSize = reflectionSize;
        this.gazeFrequency = gazeFrequency;
        this.gazeSmallMoveStrength = gazeSmallMoveStrength;
        this.gazeSmallMoveFrequency = gazeSmallMoveFrequency;
        this.gazeSpeed = gazeSpeed;
        this.gazePrideFrequency = gazePrideFrequency;
        this.gazePride = gazePride;
        this.gazeNervousness = gazeNervousness;
    }

    @Override
    public void render(PApplet context) {
        this.animate(context);
        super.render(context);
    }

    private void animate(PApplet context) {
        if (gazeBuffer == context.frameCount) {
            gazeBuffer = (int) (context.random(gazeFrequency) + 10) + context.frameCount;

            xTarget = context.random(eyeContext.getEyeSize() / 2f) - (eyeContext.getEyeSize() / 4f);
            yTarget = context.random(eyeContext.getEyeSize() / 4f) - (eyeContext.getEyeSize() / 8f);

            if ((int) context.random(gazeFrequency) % gazeSmallMoveFrequency != 0) {
                xTarget = (irisX - xTarget) * gazeSmallMoveStrength;
                yTarget = (irisY - yTarget) * gazeSmallMoveStrength;
            }

            if (context.frameCount % gazePrideFrequency != 0) {
                if ((yTarget > 0 && gazePride) || (yTarget < 0 && !gazePride)) {
                    yTarget = -yTarget;
                }
            }
        }

        xLerp = PApplet.lerp(xLerp, xTarget, gazeSpeed + context.random(gazeSpeed / 2));
        yLerp = PApplet.lerp(yLerp, yTarget, gazeSpeed + context.random(gazeSpeed / 2));

        irisX = xLerp + 10 * context.noise(wiggle);
        irisY = yLerp + 10 * context.noise(wiggle);

        reflectionX = irisX + 25f + (0.4f * irisX);
        reflectionY = irisY - 25f + (0.25f * irisY);

        wiggle += nervousnessFunction(gazeNervousness);
    }

    private float nervousnessFunction(float x) {
        return (float) (0.003142857 * Math.pow(x, 2) - 0.01225714 * x + 0.0114);
    }
}
