package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BoFactory;
import org.example.bo.asset.UserBo;
import org.example.util.BoType;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXCheckBox cbShowPassword;

    @FXML
    private TextField txtShowPassword;

    @FXML
    private AnchorPane loginWindow;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    private final UserBo userBo;

    private final ScenseSwitchController sceneSwitch;


    public LoginFormController(Hyperlink linkForgotPassword, TextField txtShowPassword) {
        this.userBo = BoFactory.getInstance().getBo(BoType.USER);
        this.sceneSwitch = ScenseSwitchController.getInstance();
    }

    @FXML
    void ForgotPasswordOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(loginWindow,"ForgotPasswordForm.fxml");

    }

    @FXML
    void SignUpOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(loginWindow,"signUpForm.fxml");
    }

    @FXML
    void btnSignInOnAction(ActionEvent event) throws IOException {
        if (txtUserName.getText().isEmpty() && txtPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Text fields can't be empty!").show();
        } else {
            if (userBo.checkIfUserPasswordMatches(txtUserName.getText(), txtPassword.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Login Success").show();
                sceneSwitch.switchScene(loginWindow,"dashboardForm.fxml");
            } else {
                new Alert(Alert.AlertType.ERROR, "Username, Password didn't match. Try again...").show();
            }

        }

    }

    @FXML
    void cbShowPasswordOnAction(ActionEvent event) {

        if (cbShowPassword.isSelected()){
            txtShowPassword.setText(txtPassword.getText());
            txtPassword.setVisible(false);
            txtShowPassword.setVisible(true);
        } else {
            txtPassword.setVisible(true);
            txtShowPassword.setVisible(false);
        }

    }
}
