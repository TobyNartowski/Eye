package pl.tobynartowski;

import pl.tobynartowski.component.eyelid.EyelidAnimated;
import pl.tobynartowski.component.iris.IrisAnimated;
import pl.tobynartowski.component.iris.IrisReflectionType;
import pl.tobynartowski.component.sclera.Sclera;
import pl.tobynartowski.utils.color.Color;
import pl.tobynartowski.utils.color.ColorPaletteFactory;
import processing.core.PApplet;

public class EyeApplication extends PApplet {

    private final EyeConfiguration configuration =
            EyeConfiguration.builder()
                    .windowSize(712)
                    .frameRate(60)
                    .eyeSize(200)
                    .colorPalette(
                            ColorPaletteFactory.createColorPalette(
                                    ColorPaletteFactory.DEFAULT_SATURATION))
                    .build();

    private Sclera sclera;
    private IrisAnimated iris;
    private EyelidAnimated eyelid;

    @Override
    public void settings() {
        EyeConfiguration.setInstance(configuration);
        size(configuration.getWindowSize(), configuration.getWindowSize());
    }

    @Override
    public void setup() {
        frameRate(configuration.getFrameRate());
        colorMode(HSB, 360, 100, 100, 100);
        noStroke();

        sclera =
                Sclera.builder()
                        .color(configuration.getColorPalette().getLightColor())
                        .width(configuration.getEyeSize() * 2f)
                        .height(configuration.getEyeSize() * 1.75f)
                        .build();
        iris =
                IrisAnimated.builder()
                        .irisColor(configuration.getColorPalette().getFirstAccentColor())
                        .irisSize(configuration.getEyeSize())
                        .pupilColor(configuration.getColorPalette().getDarkColor())
                        .pupilSize(configuration.getEyeSize() * 0.75f)
                        .reflectionType(IrisReflectionType.JELLY)
                        .reflectionSize(configuration.getEyeSize() * 0.025f)
                        .reflectionColor(Color.of(180, 0, 100))
                        .build();
        eyelid =
                EyelidAnimated.builder()
                        .eyelashColor(configuration.getColorPalette().getSecondAccentColor())
                        .eyelashQuantity((int) random(5) + 13)
                        .eyelashWeight(6f)
                        .eyelidHeight(25f)
                        .lowerEyelidColor(configuration.getColorPalette().getSecondAccentColor())
                        .upperEyelidColor(configuration.getColorPalette().getSecondAccentColor())
                        .build();
    }

    @Override
    public void draw() {
        background(
                configuration.getColorPalette().getBackgroundColor().getHue(),
                configuration.getColorPalette().getBackgroundColor().getSaturation(),
                configuration.getColorPalette().getBackgroundColor().getBrightness());

        pushMatrix();
        translate(configuration.getWindowSize() / 2f, configuration.getWindowSize() / 2f);

        sclera.render(this);
        iris.render(this);
        eyelid.render(this);

        popMatrix();

        logFrameRate();
    }

    private void logFrameRate() {
        if (frameCount % 100 == 0) {
            System.out.printf(
                    "EyeApplication - frameCount=[%s] frameRate=[%s]%n", frameCount, frameRate);
        }
    }

    public static void main(String[] args) {
        PApplet.main(EyeApplication.class.getName(), args);
    }
}
