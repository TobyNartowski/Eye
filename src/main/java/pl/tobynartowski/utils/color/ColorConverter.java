package pl.tobynartowski.utils.color;

import java.awt.Color;

class ColorConverter {

    static pl.tobynartowski.utils.color.Color convertToHsbColor(
            int red, int green, int blue) {
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);

        float hue = hsb[0] * 360;
        float saturation = hsb[1] * 100;
        float brightness = hsb[2] * 100;

        return pl.tobynartowski.utils.color.Color.of(hue, saturation, brightness);
    }
}
