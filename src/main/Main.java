package main;

import frontend.FXApplication;
import frontend.Systemtray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by berg on 18/07/15.
 */
public class Main extends javafx.application.Application{

    ArrayList<Stream> streamlist = new ArrayList();
    Systemtray tray;

    private Stage primaryStage;


    public static void main(String[] args) {

        Main main = new Main();
        launch();
    }
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initMainLayout();
    }

    public void initMainLayout() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("scene.fxml"));
            AnchorPane anchor = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(anchor);
            primaryStage.setScene(scene);
            primaryStage.show();

            SceneController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Main() {


        //Stream handler = new Stream("esl_csgo");
        //streamlist.add(handler);

        //tray = new Systemtray();
        //mainLoop();
    }

    private void mainLoop() {

        Thread thread = new Thread();
        while(true) {
            for (Stream stream : streamlist) {
                if (stream.getInternetConnection()) {
                    Boolean online = stream.isOnline();

                    if (!stream.getLastOnlineStatus() && online) {
                        tray.displayPopup(stream.getStreamerName(), stream.getStreamHeader());
                        stream.setLastOnlineStatus(true);
                    } else if (stream.getLastOnlineStatus() && !online) {
                        stream.setLastOnlineStatus(false);
                    }
                }
            }
            try {
                thread.sleep(10000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
