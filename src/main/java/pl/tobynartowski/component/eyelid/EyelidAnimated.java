package pl.tobynartowski.component.eyelid;

import lombok.Builder;
import pl.tobynartowski.util.color.Color;
import processing.core.PApplet;

import java.util.List;
import java.util.Random;

public class EyelidAnimated extends Eyelid {

    private static final int BLINK_FREQUENCY_CONSTANT = 200;

    private final Random random = new Random();
    private final List<Integer> blinkMultipliers = List.of(2, 4, 6, 8);

    private boolean isBlinking = false;
    private int nextBlink = 0;
    private float shift = 0;
    private int blinkMultiplier = 4;

    private final int blinkingFrequency;
    private final float blinkingSpeed;

    @Builder
    private EyelidAnimated(
            Color eyelashColor,
            int eyelashQuantity,
            float eyelashWeight,
            float eyelidHeight,
            Color upperEyelidColor,
            Color lowerEyelidColor,
            int blinkingFrequency,
            float blinkingSpeed) {
        this.eyelashColor = eyelashColor;
        this.eyelashQuantity = eyelashQuantity;
        this.eyelashWeight = eyelashWeight;
        this.eyelidHeight = eyelidHeight;
        this.upperEyelidColor = upperEyelidColor;
        this.lowerEyelidColor = lowerEyelidColor;
        this.blinkingFrequency = blinkingFrequency;
        this.blinkingSpeed = blinkingSpeed;
    }

    @Override
    public void render(PApplet context) {
        this.animate(context);
        super.render(context);
    }

    private void animate(PApplet context) {
        float actualShift = (float) (-Math.pow(Math.cos(3 * shift), blinkMultiplier) + 1) * createBlinkMultiplier();

        upperEyelidDeformation = -((configuration.getEyeSize() / 2f) - (actualShift * 1.75f));
        lowerEyelidDeformation = (configuration.getEyeSize() / 2f) - (actualShift * 0.3f);
        eyelashDeformation = upperEyelidDeformation - eyelidHeight - (eyelidHeight / 1.75f);

        if (isBlinking && context.frameCount >= nextBlink) {
            blink();
        }

        if (!isBlinking) {
            nextBlink =
                    context.frameCount
                            + random.nextInt((BLINK_FREQUENCY_CONSTANT * 5) / blinkingFrequency)
                            + BLINK_FREQUENCY_CONSTANT / blinkingFrequency;
            blinkMultiplier = createRandomBlinkPower();
            isBlinking = true;
        }
    }

    private void blink() {
        shift += blinkingSpeed;
        if (shift >= Math.PI / 3) {
            shift = 0;
            isBlinking = false;
        }
    }

    private int createRandomBlinkPower() {
        return blinkMultipliers.get(random.nextInt(blinkMultipliers.size()));
    }

    private float createBlinkMultiplier() {
        return configuration.getEyeSize() >= 200f ? 110f : 98f;
    }
}
