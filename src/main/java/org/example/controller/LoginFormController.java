package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BoFactory;
import org.example.bo.asset.UserBo;
import org.example.util.BoType;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginWindow;
    public TextField txtShowPassword;
    public JFXPasswordField txtPassword;
    public JFXCheckBox cbShowPassword;
    public Hyperlink linkForgotPassword;
    public JFXButton btnSignUp;
    public JFXTextField txtUserName;
    public JFXButton btnSignIn;

    private final UserBo userBo;

    private final ScenseSwitchController sceneSwitch;


    public LoginFormController() {
        this.userBo = BoFactory.getInstance().getBo(BoType.USER);
        this.sceneSwitch = ScenseSwitchController.getInstance();
    }

    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {

        if (txtUserName.getText().isEmpty() && txtPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Text fields can't be empty!").show();
        } else {
            if (userBo.checkIfUserPasswordMatches(txtUserName.getText(), txtPassword.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Login Success").show();
                sceneSwitch.switchScene(loginWindow,"dashboard-form.fxml");
            } else {
                new Alert(Alert.AlertType.ERROR, "Username, Password didn't match. Try again...").show();
            }

        }
    }

    public void cbShowPasswordOnAction(ActionEvent actionEvent) {
        if (cbShowPassword.isSelected()){
            txtShowPassword.setText(txtPassword.getText());
            txtPassword.setVisible(false);
            txtShowPassword.setVisible(true);
        } else {
            txtPassword.setVisible(true);
            txtShowPassword.setVisible(false);
        }
    }

    public void ForgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(loginWindow,"forgotPasswordForm.fxml");
    }

    public void SignUpOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(loginWindow,"signUpForm.fxml");
    }
}
