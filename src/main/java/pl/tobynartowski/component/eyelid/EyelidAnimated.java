package pl.tobynartowski.component.eyelid;

import lombok.Builder;
import pl.tobynartowski.utils.color.Color;
import processing.core.PApplet;

public class EyelidAnimated extends Eyelid {

    private final float shiftChange = 0.01f;
    private float shift = shiftChange;

    @Builder
    private EyelidAnimated(
            Color eyelashColor,
            int eyelashQuantity,
            float eyelashWeight,
            float eyelidHeight,
            Color upperEyelidColor,
            Color lowerEyelidColor) {
        this.eyelashColor = eyelashColor;
        this.eyelashQuantity = eyelashQuantity;
        this.eyelashWeight = eyelashWeight;
        this.eyelidHeight = eyelidHeight;
        this.upperEyelidColor = upperEyelidColor;
        this.lowerEyelidColor = lowerEyelidColor;
    }

    @Override
    public void render(PApplet context) {
        this.animate();
        super.render(context);
    }

    private void animate() {
        float actualShift = (float) (100 * Math.exp(-80 * Math.pow(4 * Math.sin(shift), 2)));

        upperEyelidDeformation = -((configuration.getEyeSize() / 2f) - (actualShift * 1.75f));
        lowerEyelidDeformation = (configuration.getEyeSize() / 2f) - (actualShift * 0.3f);
        eyelashDeformation = upperEyelidDeformation - eyelidHeight - (eyelidHeight / 1.75f);

        shift += shiftChange;
    }
}
