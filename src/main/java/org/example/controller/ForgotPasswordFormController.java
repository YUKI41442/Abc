package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BoFactory;
import org.example.bo.asset.UserBo;
import org.example.util.BoType;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgotPasswordFormController implements Initializable {
    public JFXPasswordField pfReEnterPassword;
    public AnchorPane forgotPasswordWindow;
    public JFXButton btnSendOtp;
    public JFXTextField txtEmail;
    public JFXPasswordField pfPassword;
    public JFXButton btnReset;
    public JFXTextField txtOtp;
    public CheckBox cbShowPassword;
    public TextField txtPassword;

    public TextField txtReEnterPassword;

    private final UserBo userBo;

    private final ScenseSwitchController sceneSwitch;

    private int otp;

    public ForgotPasswordFormController() {
        this.userBo = BoFactory.getInstance().getBo(BoType.USER);
        this.sceneSwitch = ScenseSwitchController.getInstance();
    }

    public void btnSendOtpOnAction(ActionEvent actionEvent) {

        if (!txtEmail.getText().isEmpty()) {
            Random random = new Random();
            otp = random.nextInt(999999)+100000;

            try {
                userBo.sendEmail(txtEmail.getText(), Integer.toString(otp));
                new Alert(Alert.AlertType.INFORMATION,"Send Email Successfully").show();
                btnReset.setDisable(false);
                pfPassword.setEditable(true);
                pfReEnterPassword.setEditable(true);
            } catch (MessagingException e) {
                new Alert(Alert.AlertType.ERROR,"Access denied... your Email is invalid").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws IOException {

        if (
                pfPassword.getText().isEmpty()
                        && pfReEnterPassword.getText().isEmpty()
                        && txtOtp.getText().isEmpty()
        ) {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        } else {
            if (pfPassword.getText().equals(pfReEnterPassword.getText())) {
                if (Integer.parseInt(txtOtp.getText()) == otp) {
                    if (
                            userBo.updatePasswordByEmail(txtEmail.getText(),
                                    userBo.passwordEncrypt(txtPassword.getText()))

                    ) {
                        new Alert(Alert.AlertType.INFORMATION, "Password Reset Successfully").show();
                        sceneSwitch.switchScene(forgotPasswordWindow,"login-form.fxml");
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Password couldn't reset").show();
                    }
                }
            }
        }
    }

    public void cbShowPasswordOnAction(ActionEvent actionEvent) {

            if (cbShowPassword.isSelected()){
                txtPassword.setText(pfPassword.getText());
                pfPassword.setVisible(false);
                txtReEnterPassword.setText(pfReEnterPassword.getText());
                pfReEnterPassword.setVisible(false);
            } else {
                pfPassword.setVisible(true);
                pfReEnterPassword.setVisible(true);
            }
        }


    public void BackToLoginOnAction(ActionEvent actionEvent) throws IOException {

        sceneSwitch.switchScene(forgotPasswordWindow,"loginForm.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
