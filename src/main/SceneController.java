package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Created by berg on 18/07/15.
 */
public class SceneController {

    private Main main;
    @FXML
    private Button Button;
    @FXML
    private TextField textField;
    @FXML
    private ListView listView;

    public SceneController() {
        System.out.println("SceneController created");

    }
    @FXML
    private void initialize() {
        System.out.println("controller accessed");
    }

    public void setMain(Main main) {
        this.main = main;
    }
    @FXML
    public void buttonClicked() {
        String getText = textField.getText();
        main.addStreamer(getText);
        textField.setText("");
        list();
    }

    @FXML
    public void textInput() {

    }

    @FXML
    public void list() {
        ObservableList<Stream> tempList = FXCollections.observableList(main.streamlist);
        listView.setItems(tempList);
    }
}
