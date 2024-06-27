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
    public JFXButton btnSignIn;

    @FXML
    public JFXCheckBox cbShowPassword;

    @FXML
    public AnchorPane signUpWindow;

    @FXML
    public JFXPasswordField txtPassword;

    @FXML
    public JFXTextField txtUserName;

    @FXML
    public JFXTextField txtUserName1;

    @FXML
    public JFXTextField txtUserName11;

    @FXML
    public TextField txtEmail;

    @FXML
    public TextField txtAddress;
    @FXML
    public TextField txtName;

    @FXML
    public TextField txtShowPassword;


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
    void btnSignInOnAction(ActionEvent event) throws IOException{

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
                sceneSwitch.switchScene(signUpWindow,"login-form.fxml");
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
