package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        System.out.println(getText);
    }

    @FXML
    public void textInput() {


    }
}
