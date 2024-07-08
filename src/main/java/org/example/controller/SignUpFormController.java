package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BoFactory;
import org.example.bo.asset.UserBo;
import org.example.model.User;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private JFXCheckBox cbShowPassword;

    @FXML
    private AnchorPane signUpWindow;

    @FXML
    private TextField txtShowPassword;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXPasswordField txtPassword;

    private final UserBo userBo;
    private final ScenseSwitchController sceneSwitch;

    public SignUpFormController() {
        this.userBo = BoFactory.getInstance().getBo(BoType.USER);
        this.sceneSwitch = ScenseSwitchController.getInstance();
    }

    @FXML
    void BackToLoginOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(signUpWindow,"loginForm.fxml");
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        if (
                txtName.getText().isEmpty()
                        && txtEmail.getText().isEmpty()
                        && txtAddress.getText().isEmpty()
                        && txtPassword.getText().isEmpty()
        ) {
            new Alert(Alert.AlertType.ERROR, "Text Fields can't be empty!").show();
        } else {
            String email = txtEmail.getText();
            if (userBo.isValidEmail(email)){
                User user = new User(
                        userBo.generateEmployeeId(),
                        txtName.getText(),
                        email,
                        txtAddress.getText(),
                        userBo.passwordEncrypt(txtPassword.getText())
                );

                userBo.insertUser(user);

                new Alert(Alert.AlertType.INFORMATION, "Sign Up Success").show();
                sceneSwitch.switchScene(signUpWindow,"loginForm.fxml");
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Email. Try again...").show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
