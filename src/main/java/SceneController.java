import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.net.URI;


/**
 * Created by berg on 18/07/15.
 */
public class SceneController {

    private Main main;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button openStream;
    @FXML
    private TextField textField;
    @FXML
    private TableView streamView;
    @FXML
    private TableColumn streamList;
    @FXML
    private TableColumn gameList;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label userLabel, gameLabel, titleLabel, statusLabel, viewersLabel;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView banner;

    public SceneController() {
        System.out.println("SceneController created");

    }

    /**
     * Initializes the view.
     */
    @FXML
    public void initialize() {

        streamList.setCellValueFactory(new PropertyValueFactory<Stream, String>("streamerName"));
        gameList.setCellValueFactory(new PropertyValueFactory<Stream, String>("game"));
        System.out.println("Controller initialized");
    }

    /**
     * Sets the reference main class as a reference for this controller
     * @param main main class
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * Adds the channel name in the textfield to the stream list.
     */
    @FXML
    public void addClicked() {
        if (textField.getText().length() != 0) {
            String getText = textField.getText();
            main.addStreamer(getText);
            textField.setText("");
            list();
        }
    }

    /**
     * Removes the selected channel name in the list from the stream list.
     */
    @FXML
    public void deleteClicked() {
        if (main.getStreamList().size() != 0 && streamView.getSelectionModel().getSelectedItem() != null) {
            Stream selected = (Stream) streamView.getSelectionModel().getSelectedItem();
            main.getStreamList().remove(main.getStreamList().indexOf(selected));
            list();
            streamView.getSelectionModel().selectNext();
        }
    }

    @FXML
    public void textInput() {

    }
    /**
     * Sets the items from the stream list to the view.
     */
    @FXML
    public void list() {
        Collections.sort(main.getStreamList());
        ObservableList<Stream> tempList = FXCollections.observableList(main.getStreamList());
        streamView.setItems(tempList);
    }
    @FXML
    public void streamClicked() {
        if (streamView.getSelectionModel().getSelectedItem() != null) {
            Stream s = (Stream) streamView.getSelectionModel().getSelectedItem();

            gridPane.getChildren().remove(userLabel);
            userLabel = new Label(s.getStreamerName());
            gridPane.add(userLabel, 1, 0);

            gridPane.getChildren().remove(statusLabel);
            if (s.onlineStatus) {
                statusLabel = new Label("Online");
                gridPane.getChildren().remove(gameLabel);
                gameLabel = new Label(s.getGame());
                gridPane.add(gameLabel, 1, 2);

                gridPane.getChildren().remove(titleLabel);
                titleLabel = new Label(s.getStreamHeader());
                gridPane.add(titleLabel, 1, 3);

                gridPane.getChildren().remove(viewersLabel);
                viewersLabel = new Label(s.getViewers());
                gridPane.add(viewersLabel, 1, 4);
            }
            else {
                statusLabel = new Label("Offline");
                gridPane.getChildren().remove(gameLabel);
                gameLabel = new Label("");
                gridPane.add(gameLabel, 1, 2);

                gridPane.getChildren().remove(titleLabel);
                titleLabel = new Label("");
                gridPane.add(titleLabel, 1, 3);

                gridPane.getChildren().remove(viewersLabel);
                viewersLabel = new Label("");
                gridPane.add(viewersLabel, 1, 4);

            }
            gridPane.add(statusLabel, 1, 1);
            if (s.getStreamLogo() != null) {
                logo.setImage(s.getStreamLogo());
            }
            else {
                logo.setImage(null);
            }
            if (s.getStreamBanner() != null) {
                banner.setImage(s.getStreamBanner());
            }
            else {
                banner.setImage(null);
            }
        }
    }

    /**
     * Invoked when the "Open Stream" button is pressed. Opens the stream in the browser.
     */
    @FXML
    public void openStream() {
        if (streamView.getSelectionModel().getSelectedItem() != null) {
            Stream s = (Stream) streamView.getSelectionModel().getSelectedItem();
            String url = s.getStreamURL();
            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URL(url).toURI());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
