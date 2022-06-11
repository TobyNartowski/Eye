package pl.tobynartowski;

import pl.tobynartowski.component.eyelid.Eyelid;
import pl.tobynartowski.component.eyelid.EyelidAnimated;
import pl.tobynartowski.component.iris.Iris;
import pl.tobynartowski.component.iris.IrisAnimated;
import pl.tobynartowski.component.sclera.Sclera;
import pl.tobynartowski.configuration.property.ConfigurationPropertyMapper;
import pl.tobynartowski.util.color.Color;
import pl.tobynartowski.util.color.ColorPaletteFactory;
import pl.tobynartowski.util.recorder.Recorder;
import processing.core.PApplet;

public class EyeApplication extends PApplet {

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
                        .eyeSize(ConfigurationPropertyMapper.getFloatMappedValue(EyeContext.Fields.eyeSize))
                        .colorPalette(ColorPaletteFactory.createColorPalette(ConfigurationPropertyMapper.getFloatMappedValue(EyeContext.Fields.colorIntensifier)))
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
                        .width(ConfigurationPropertyMapper.getFloatMappedValue(EyeContext.Fields.eyeSize) * 1.99f)
                        .height(ConfigurationPropertyMapper.getFloatMappedValue(EyeContext.Fields.eyeSize) * 1.75f)
                        .build();
        iris =
                IrisAnimated.builder()
                        .irisColor(configuration.getColorPalette().getFirstAccentColor())
                        .irisSize(ConfigurationPropertyMapper.getFloatMappedValue(EyeContext.Fields.eyeSize))
                        .pupilColor(configuration.getColorPalette().getDarkColor())
                        .pupilSize(ConfigurationPropertyMapper.getFloatMappedValue(EyeContext.Fields.eyeSize)
                                * ConfigurationPropertyMapper.getFloatMappedValue(Iris.Fields.pupilSize))
                        .reflectionType(50)
                        .reflectionSize(ConfigurationPropertyMapper.getFloatMappedValue(EyeContext.Fields.eyeSize) * 0.025f)
                        .reflectionColor(Color.of(180, 0, 100))
                        // TODO: Configure
                        .gazeFrequency(125f) // [50-200] - [low-high]
                        .gazeSmallMoveStrength(0.25f) // [0-0.5] - [none-big]
                        .gazeSmallMoveFrequency(4) //[1-6] - [none-high]
                        .gazeSpeed(0.17f) // [0.08-0.25] - [slow-fast]
                        .gazePrideFrequency(2) // [1-6] - [none-high]
                        .gazePride(true) // [false-true] - [depressed-proud]
                        .gazeNervousness(3f) // [2-5] - [calm-nervous]
                        .build();
        eyelid =
                EyelidAnimated.builder()
                        .eyelashColor(configuration.getColorPalette().getSecondAccentColor())
                        .eyelashQuantity((int) (random(3) + ConfigurationPropertyMapper.getIntegerMappedValue(Eyelid.Fields.eyelashQuantity)))
                        .eyelashWeight(ConfigurationPropertyMapper.getFloatMappedValue(Eyelid.Fields.eyelashWeight))
                        .eyelidHeight(ConfigurationPropertyMapper.getFloatMappedValue(Eyelid.Fields.eyelidHeight))
                        .lowerEyelidColor(configuration.getColorPalette().getSecondAccentColor())
                        .upperEyelidColor(configuration.getColorPalette().getSecondAccentColor())
                        // TODO: Configure
                        .blinkingFrequency(4) // [1-10] - [low-high]
                        .blinkingSpeed(0.08f) // [0.03-0,1] - [slow-fast]
                        .build();

        // TODO: Debug - uncomment
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
        //        recorder.record(this); // TODO: Debug - uncomment
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
