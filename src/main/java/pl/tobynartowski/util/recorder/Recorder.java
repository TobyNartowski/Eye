package pl.tobynartowski.util.recorder;

import processing.core.PApplet;

public class Recorder {

    private static final String FILENAME = "export.mp4";
    private final VideoExport videoExport;
    private final int videoDuration;

    public Recorder(PApplet context, int seconds, int frameRate) {
        videoDuration = seconds * frameRate;
        videoExport = new VideoExport(context, FILENAME);
        videoExport.setFrameRate(frameRate);
        videoExport.startMovie();

        System.out.printf(
                "EyeApplication - Video recording starts with total duration=[%s] and frameRate=[%s]\n",
                videoDuration, frameRate);
    }

    public void record(PApplet context) {
        if (context.frameCount >= videoDuration) {
            System.out.printf(
                    "EyeApplication - video=[%s] with duration=[%s secs] saved.\n",
                    FILENAME, videoExport.getCurrentTime());
            videoExport.endMovie();
            context.exit();
        } else {
            videoExport.saveFrame();
        }
    }
}
