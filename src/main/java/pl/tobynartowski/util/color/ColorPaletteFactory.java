package pl.tobynartowski.util.color;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ColorPaletteFactory {

    public static final float DEFAULT_SATURATION = 1f;

    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();

    public static ColorPalette createColorPalette(float saturation) {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body =
                    RequestBody.create(
                            "{\"input\":[\"N\",\"N\",[238,238,238],\"N\",\"N\"],\"model\":\"default\"}",
                            mediaType);
            Request request =
                    new Request.Builder()
                            .url("http://colormind.io/api/")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .build();
            Response response = client.newCall(request).execute();

            String responseJson = Objects.requireNonNull(response.body()).string();
            JsonNode nodes = new ObjectMapper().readTree(responseJson).get("result");
            List<Color> extractedColors = new ArrayList<>();

            for (JsonNode jsonColor : nodes) {
                extractedColors.add(
                        ColorConverter.convertToHsbColor(
                                jsonColor.get(0).asInt(),
                                jsonColor.get(1).asInt(),
                                jsonColor.get(2).asInt()));
            }

            ColorPalette colorPalette = extractColorPaletteFromColors(extractedColors);
            return gradeColorPalette(colorPalette, saturation);
        } catch (IOException e) {
            throw new ColorPaletteFactoryServerException(
                    String.format("Color palette server error [%s]", e.getLocalizedMessage()));
        }
    }

    private static ColorPalette extractColorPaletteFromColors(List<Color> extractedColors) {
        ColorPalette colorPalette = new ColorPalette();

        extractedColors.sort(Comparator.comparing(Color::getBrightness).reversed());
        colorPalette.setLightColor(extractedColors.get(0));
        extractedColors.remove(0);

        Color extractedDarkColor = extractedColors.get(extractedColors.size() - 1);
        Color darkColor = makeDarkerColor(extractedDarkColor, 2);
        colorPalette.setDarkColor(darkColor);

        Color backgroundColor = makeDarkerColor(extractedDarkColor, 3);
        colorPalette.setBackgroundColor(backgroundColor);
        extractedColors.remove(extractedColors.size() - 1);

        extractedColors.sort(Comparator.comparing(Color::getSaturation).reversed());
        colorPalette.setFirstAccentColor(extractedColors.get(0));
        colorPalette.setSecondAccentColor(extractedColors.get(1));

        return colorPalette;
    }

    private static ColorPalette gradeColorPalette(ColorPalette colorPalette, float saturation) {
        ColorPalette gradedColorPalette = new ColorPalette();
        gradedColorPalette.setLightColor(gradeColor(colorPalette.getLightColor(), saturation));
        gradedColorPalette.setDarkColor(gradeColor(colorPalette.getDarkColor(), saturation));
        gradedColorPalette.setBackgroundColor(gradeColor(colorPalette.getBackgroundColor(), saturation));
        gradedColorPalette.setFirstAccentColor(gradeColor(colorPalette.getFirstAccentColor(), saturation));
        gradedColorPalette.setSecondAccentColor(gradeColor(colorPalette.getSecondAccentColor(), saturation));

        return gradedColorPalette;
    }

    private static Color gradeColor(Color original, float saturation) {
        return Color.of(
                original.getHue(),
                safeMultiply(original.getSaturation(), saturation),
                original.getBrightness());
    }

    private static float safeMultiply(float a, float b) {
        float result = a * b;
        return result > 100 ? 100 : Math.max(result, 1);
    }

    private static Color makeDarkerColor(Color original, float divider) {
        return Color.of(
                original.getHue(), original.getSaturation(), original.getBrightness() / divider);
    }
}
