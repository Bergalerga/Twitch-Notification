import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.Collections;


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

    public SceneController() {
        System.out.println("SceneController created");

    }

    /**
     * Initializes the view.
     */
    @FXML
    private void initialize() {

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
        if (main.getStreamList().size() != 0) {
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

            gridPane.getChildren().remove(titleLabel);
            titleLabel = new Label(s.getStreamHeader());
            gridPane.add(titleLabel, 1, 1);

            gridPane.getChildren().remove(gameLabel);
            gameLabel = new Label(s.getGame());
            gridPane.add(gameLabel, 1, 2);

            gridPane.getChildren().remove(statusLabel);
            statusLabel = new Label(s.onlineStatus.toString());
            gridPane.add(statusLabel, 1, 3);

            gridPane.getChildren().remove(viewersLabel);
            viewersLabel = new Label(s.getViewers());
            gridPane.add(viewersLabel, 1, 4);

        }
    }

}
