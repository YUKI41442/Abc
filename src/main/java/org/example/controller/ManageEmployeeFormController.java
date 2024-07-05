package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.example.bo.BoFactory;
import org.example.bo.asset.UserBo;
import org.example.model.User;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageEmployeeFormController implements Initializable {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnCustomerDetails;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnManageEmployee;

    @FXML
    private JFXButton btnOrderDetails;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnProductDetails;

    @FXML
    private JFXButton btnSupplierDetails;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<User, String> colEmployeeAddress;

    @FXML
    private TableColumn<User, String> colEmployeeEmail;

    @FXML
    private TableColumn<User, String> colEmployeeId;

    @FXML
    private TableColumn<User, String> colEmployeeName;

    @FXML
    private Text lblClothify;

    @FXML
    private AnchorPane manageEmployeeWindow;

    @FXML
    private TableView<User> tblEmployee;

    @FXML
    private JFXTextField txtEmployeeAddress;

    @FXML
    private JFXTextField txtEmployeeEmail;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtEmployeeName;

    private final UserBo userBo;

    private final ScenseSwitchController sceneSwitch;

    public ManageEmployeeFormController() {
        this.sceneSwitch = ScenseSwitchController.getInstance();
        this.userBo = BoFactory.getInstance().getBo(BoType.USER);
    }

    private void clearTextFields(){
        txtEmployeeName.setText("");
        txtEmployeeEmail.setText("");
        txtEmployeeAddress.setText("");
    }
    private void loadEmployeeTbl(){
        tblEmployee.setItems(userBo.getAllUsers());
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearTextFields();
    }


    private boolean areTextFieldsEmpty() {
        return !txtEmployeeId.getText().isEmpty() ||
                !txtEmployeeName.getText().isEmpty() ||
                !txtEmployeeEmail.getText().isEmpty() ||
                !txtEmployeeAddress.getText().isEmpty();
    }

    @FXML
    void btnCustomerDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"customerDetailsForm.fxml");
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"manageEmployeeForm.fxml");
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"orderDetailsForm.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"placeOrderForm.fxml");
    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"productDetailsForm.fxml");
    }

    @FXML
    void btnSuplierDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"supplierDetailsForm.fxml");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!areTextFieldsEmpty()) {
            User user = userBo.getUserById(txtEmployeeId.getText());

            if (user != null){
                user.setName(txtEmployeeName.getText());
                user.setAddress(txtEmployeeAddress.getText());
                user.setEmail(txtEmployeeEmail.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?");
                Optional<ButtonType> result = alert.showAndWait();
                // Check if the response was OK or Cancel
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (userBo.updateUser(user)) {
                        new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully").show();
                    }else {
                        new Alert(Alert.AlertType.ERROR, "Update failed. Try again...").show();
                    }
                }
            }else {
                new Alert(Alert.AlertType.WARNING, "User does not exists...").show();
            }
            loadEmployeeTbl();
            clearTextFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (!areTextFieldsEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            // Check if the response was OK or Cancel
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (userBo.deleteUserById(txtEmployeeId.getText())) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Delete failed. Try again...").show();
                }
            }
            loadEmployeeTbl();
            clearTextFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmployeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadEmployeeTbl();
    }
}
