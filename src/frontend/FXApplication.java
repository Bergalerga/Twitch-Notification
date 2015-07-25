package frontend;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by berg on 20/07/15.
 */
public class FXApplication extends javafx.application.Application {

    public FXApplication() {

        initApplication();
        launch();
    }
    @Override
    public void start(Stage stage) {
        Parent root = FXMLLoader.load(getClass().getResource("static"));
    }

    private void initApplication() {


    }


}
