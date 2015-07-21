package frontend;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by berg on 20/07/15.
 */
public class InitApplication extends Application {

    public InitApplication() {

        initLogin();
        initSystemTray();
    }
    @Override
    public void start(Stage stage) {

    }

    private void initLogin() {


    }

    private void initSystemTray() {
        if (!Systemtray.isSupported()) {
            System.out.println("Systemtray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("static/img/trayIcon.png"));
        } catch (IOException e) {
            System.out.println("Could not read file");
        }

        final TrayIcon trayIcon = new TrayIcon(img);
        final Systemtray tray = Systemtray.getSystemTray();

        MenuItem exit = new MenuItem("exit");

        try {
            tray.add(trayIcon);
        }
        catch (AWTException e) {
            System.out.println("Could not add trayIcon");
        }
    }
}
