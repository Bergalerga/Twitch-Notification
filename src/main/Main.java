package main;

import frontend.FXApplication;
import frontend.Systemtray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by berg on 18/07/15.
 */
public class Main extends javafx.application.Application{

    ArrayList<Stream> streamlist = new ArrayList();
    Systemtray tray;
    Thread t1;

    private Stage primaryStage;
    private BorderPane rootLayout;


    public static void main(String[] args) {

        Main main = new Main();

        //launch();

    }
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Twitch Notification");


        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("RootLayout.fxml"));
            // Show the scene containing the root layout.
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            //SceneController controller = loader.getController();
            //controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        showStreamOverview();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Shows the person overview scene.
     */
    public void showStreamOverview() {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("streamOverview.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);

        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    public Main() {
        addStreamer("zfg1");
        mainLoop();

    }

    private void mainLoop() {
        t1 = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    for (Stream stream : streamlist) {
                        if (stream.getInternetConnection()) {
                            Boolean copy = stream.onlineStatus;
                            stream.updateStreamStatus();
                            if (stream.onlineStatus && !copy) {
                                tray.displayPopup(stream.getChannelName(), stream.getStreamHeader());
                            }
                        }
                    }
                    Collections.sort(streamlist);
                    System.out.println(streamlist);
                    try {
                        t1.sleep(10000);
                    }
                    catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
    }

    public void addStreamer(String channelName) {
        Stream handler = new Stream(channelName);
        if (handler.getStreamValidity()) {
            streamlist.add(handler);
        }
    }


}
