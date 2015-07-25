package main;

import frontend.FXApplication;
import frontend.Systemtray;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by berg on 18/07/15.
 */
public class Main extends javafx.application.Application{

    ArrayList<Stream> streamlist = new ArrayList();
    Systemtray tray;

    public static void main(String[] args) {

        Main main = new Main();
        FXApplication front = new FXApplication();
        launch();
    }
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("resources/scene.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Twitch Notification");
        primaryStage.show();
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
