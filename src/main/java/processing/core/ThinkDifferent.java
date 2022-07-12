package processing.core;

import java.awt.*;
import java.awt.desktop.QuitEvent;
import java.awt.desktop.QuitHandler;
import java.awt.desktop.QuitResponse;

public class ThinkDifferent {

    static boolean attemptedQuit;

    public static void init(final PApplet sketch) {
        Desktop desktop = Desktop.getDesktop();
        desktop.setQuitHandler(
                new QuitHandler() {
                    @Override
                    public void handleQuitRequestWith(QuitEvent e, QuitResponse response) {
                        sketch.exit();
                        if (PApplet.uncaughtThrowable == null && !attemptedQuit) {
                            response.cancelQuit();
                            attemptedQuit = true;
                        } else {
                            response.performQuit();
                        }
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