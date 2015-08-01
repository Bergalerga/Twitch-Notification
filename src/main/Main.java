package main;

import frontend.Systemtray;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by berg on 18/07/15.
 */
public class Main extends javafx.application.Application{

    private ArrayList<Stream> streamlist = new ArrayList();
    Systemtray tray;
    Thread t1;

    private Stage primaryStage;
    private BorderPane rootLayout;


    public static void main(String[] args) {

        //Main main = new Main();
        launch();

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

    public void stop() {
        System.out.println("Exit");
        //saveFile();
        System.exit(0);
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
            SceneController controller = loader.getController();
            controller.setMain(this);
            rootLayout.setCenter(overviewPage);

        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    public Main() {
        //loadFile();
        //mainLoop();
    }

    private void mainLoop() {
        t1 = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    for (Stream stream : getStreamList()) {
                        if (stream.getInternetConnection()) {
                            Boolean copy = stream.onlineStatus;
                            stream.updateStreamStatus();
                            if (stream.onlineStatus && !copy) {
                                tray.displayPopup(stream.getChannelName(), stream.getStreamHeader());
                            }
                        }
                    }
                    Collections.sort(getStreamList());
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
        Stream handler = new Stream(channelName.toLowerCase());
        if (handler.getStreamValidity()) {
            for (Stream s : getStreamList()) {
                if (s.getChannelName().equals(handler.getChannelName())) {
                    return;
                }
            }
            streamlist.add(handler);
        }
    }
    public synchronized ArrayList<Stream> getStreamList() {
        notifyAll();
        return streamlist;

    }
    public void saveFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("save.txt");
        } catch (FileNotFoundException e) {

        }
        for (Stream s : streamlist) {
            writer.println(s.toString());
        }
        writer.close();
    }

    public void loadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("save.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(",");
                line = br.readLine();
            }
            String everything = sb.toString();
            System.out.println(everything);
            List<String> items = Arrays.asList(everything.split("\\s*,\\s*"));
            for (String str : items) {
                if (!str.equals("")) {
                    addStreamer(str);
                }
            }
            br.close();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
