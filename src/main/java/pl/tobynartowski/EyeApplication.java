package pl.tobynartowski;

import pl.tobynartowski.component.eyelid.EyelidAnimated;
import pl.tobynartowski.component.iris.IrisAnimated;
import pl.tobynartowski.component.sclera.Sclera;
import pl.tobynartowski.configuration.mapper.FragilityMapper;
import pl.tobynartowski.configuration.property.ConfigurationProperties;
import pl.tobynartowski.util.color.Color;
import pl.tobynartowski.util.color.ColorPaletteFactory;
import pl.tobynartowski.util.recorder.Recorder;
import processing.core.PApplet;

public class EyeApplication extends PApplet {

    private final ConfigurationProperties properties = new ConfigurationProperties();
    private EyeContext configuration;
    private Recorder recorder;

    private Sclera sclera;
    private IrisAnimated iris;
    private EyelidAnimated eyelid;

    @Override
    public void settings() {
        configuration =
                EyeContext.builder()
                        .windowSize(712)
                        .frameRate(60)
                        .videoDuration(10)
                        .eyeSize(FragilityMapper.getEyeSize(properties))
                        .colorPalette(
                                ColorPaletteFactory.createColorPalette(
                                        FragilityMapper.getColor(properties)))
                        .build();

        EyeContext.setInstance(configuration);
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
                        .pupilSize(
                                configuration.getEyeSize()
                                        * FragilityMapper.getPupilSize(properties))
                        .reflectionType(FragilityMapper.getReflectionType(properties))
                        .reflectionSize(configuration.getEyeSize() * 0.025f)
                        .reflectionColor(Color.of(180, 0, 100))
                        .build();
        eyelid =
                EyelidAnimated.builder()
                        .eyelashColor(configuration.getColorPalette().getSecondAccentColor())
                        .eyelashQuantity(
                                (int) (random(4) + FragilityMapper.getEyelashQuantity(properties)))
                        .eyelashWeight(FragilityMapper.getEyelashWeight(properties))
                        .eyelidHeight(FragilityMapper.getEyelidHeight(properties))
                        .lowerEyelidColor(configuration.getColorPalette().getSecondAccentColor())
                        .upperEyelidColor(configuration.getColorPalette().getSecondAccentColor())
                        .build();

        // TODO: DEBUG, REMOVE
        //        recorder =
        //                new Recorder(
        //                        this,
        //                        (int) configuration.getVideoDuration(),
        //                        (int) configuration.getFrameRate());
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
        //        recorder.record(this); // TODO: DEBUG UNCOMMENT
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
