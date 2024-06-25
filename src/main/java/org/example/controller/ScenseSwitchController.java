package org.example.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class ScenseSwitchController {

    private static ScenseSwitchController instance;

    private ScenseSwitchController() {

    }

    public static ScenseSwitchController getInstance(){
        if(instance==null){
            return instance = new ScenseSwitchController();
        }
        return instance;
    }
    public void switchScene(AnchorPane currentAnchorPane, String fxml) throws IOException {
        AnchorPane pane  = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/" + fxml)));
        currentAnchorPane.getChildren().setAll(pane);
    }
}

}
