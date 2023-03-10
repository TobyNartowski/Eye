package processing.core;

import java.awt.*;

public class ThinkDifferent {

    static boolean attemptedQuit;

    public static void init(final PApplet sketch) {
        Desktop desktop = Desktop.getDesktop();
        desktop.setQuitHandler(
                (e, response) -> {
                    sketch.exit();
                    if (PApplet.uncaughtThrowable == null && !attemptedQuit) {
                        response.cancelQuit();
                        attemptedQuit = true;
                    } else {
                        response.performQuit();
                    }
                });
    }

    public static void cleanup() {
        Desktop.getDesktop().setQuitHandler(null);
    }

    public static void setIconImage(Image image) {
        Taskbar.getTaskbar().setIconImage(image);
    }
}