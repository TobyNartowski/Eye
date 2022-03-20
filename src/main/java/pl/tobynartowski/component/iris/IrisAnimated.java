package pl.tobynartowski.component.iris;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.tobynartowski.EyeConfiguration;
import pl.tobynartowski.utils.color.Color;
import processing.core.PApplet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IrisAnimated extends Iris {

    private final EyeConfiguration eyeConfiguration = EyeConfiguration.getInstance();

    private float xTarget = 0f;
    private float yTarget = 0f;
    private float xLerp = 0f;
    private float yLerp = 0f;
    private float wiggle = 0f;

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
        if (context.frameCount % ((int) context.random(80) + 70) == 0) {
            xTarget =
                    context.random(eyeConfiguration.getEyeSize() / 2f)
                            - (eyeConfiguration.getEyeSize() / 4f);
            yTarget =
                    context.random(eyeConfiguration.getEyeSize() / 4f)
                            - (eyeConfiguration.getEyeSize() / 8f);
        }

        xLerp = PApplet.lerp(xLerp, xTarget, 0.1f + context.random(0.12f));
        yLerp = PApplet.lerp(yLerp, yTarget, 0.1f + context.random(0.12f));

        irisX = xLerp + 10 * context.noise(wiggle);
        irisY = yLerp + 10 * context.noise(wiggle);

        reflectionX = irisX + 25f + (0.4f * irisX);
        reflectionY = irisY - 25f + (0.25f * irisY);

        wiggle += 0.005f;
    }
}
