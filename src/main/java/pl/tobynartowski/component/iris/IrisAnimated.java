package pl.tobynartowski.component.iris;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.tobynartowski.EyeContext;
import pl.tobynartowski.util.color.Color;
import processing.core.PApplet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IrisAnimated extends Iris {

    private final EyeContext eyeContext = EyeContext.getInstance();

    // TODO: Move to builder
    private float gazeFrequency = 125; // [50-200] - [low-high]
    private float gazeSmallMoveStrength = 0.25f; // [0-0.5] - [none-big]
    private int gazeSmallMoveFrequency = 2; // [1-6] - [none-high]
    private float gazeSpeed = 0.25f; // [0.03-0.25] - [slow-fast]

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
            IrisReflectionType reflectionType,
            Color reflectionColor,
            float reflectionSize) {
        this.irisColor = irisColor;
        this.irisSize = irisSize;
        this.pupilColor = pupilColor;
        this.pupilSize = pupilSize;
        this.reflectionType = reflectionType;
        this.reflectionColor = reflectionColor;
        this.reflectionSize = reflectionSize;
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
        }

        xLerp = PApplet.lerp(xLerp, xTarget, gazeSpeed + context.random(gazeSpeed / 2));
        yLerp = PApplet.lerp(yLerp, yTarget, gazeSpeed + context.random(gazeSpeed / 2));

        irisX = xLerp + 10 * context.noise(wiggle);
        irisY = yLerp + 10 * context.noise(wiggle);

        reflectionX = irisX + 25f + (0.4f * irisX);
        reflectionY = irisY - 25f + (0.25f * irisY);

        wiggle += 0.005f;
    }
}
