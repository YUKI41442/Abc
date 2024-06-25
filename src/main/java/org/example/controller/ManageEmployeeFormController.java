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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageEmployeeFormController implements Initializable {

    @FXML
    private JFXButton btnAction;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnCustomerDetails;

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
    private TableColumn<User, String> colEmployeeAddress;

    @FXML
    private TableColumn<User, String> colEmployeeEmail;

    @FXML
    private TableColumn<User, String> colEmployeeId;

    @FXML
    private TableColumn<User, String> colEmployeeName;

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

    @FXML
    private AnchorPane manageEmployeeWindow;

    private final UserBo userBo;

    private String currentId;

    private final ScenseSwitchController sceneSwitch;

    public ManageEmployeeFormController() {
        this.sceneSwitch = ScenseSwitchController.getInstance();
        this.userBo = BoFactory.getInstance().getBo(BoType.USER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEmployeeId.setEditable(false);
        loadEmployeeId();

        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmployeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadEmployeeTbl();
    }

    @FXML
    void btnActionOnAction(ActionEvent event) {

        txtEmployeeId.setEditable(true);
        txtEmployeeId.setText("");
        btnAdd.setVisible(false);
        btnClear.setVisible(false);
        btnAction.setVisible(false);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        String email = txtEmployeeEmail.getText();
        if (userBo.isValidEmail(email)){
            User user = new User(
            );

            userBo.insertUser(user);

            new Alert(Alert.AlertType.INFORMATION, "Employee Added Successfully").show();

            clearTextFields();
            loadEmployeeId();
            loadEmployeeTbl();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Email. Try again...").show();
        }
    }

    private void clearTextFields(){
        txtEmployeeName.setText("");
        txtEmployeeEmail.setText("");
        txtEmployeeAddress.setText("");
    }

    private void loadEmployeeId(){
        currentId = userBo.generateEmployeeId();
        txtEmployeeId.setText(currentId);
    }

    private void loadEmployeeTbl(){
        tblEmployee.setItems(userBo.getAllUsers());
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearTextFields();
    }

    @FXML
    void btnCustomerDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"customerDetailsForm.fxml");
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(manageEmployeeWindow,"manageEmployeeForm.fxml");
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(manageEmployeeWindow,"orderDetailsForm.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(manageEmployeeWindow,"placeOrderForm.fxml");
    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(manageEmployeeWindow,"productDetailsForm.fxml");
    }

    @FXML
    void btnSuplierDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"supplierDetailsForm.fxml");
    }

    @FXML
    void lblClothifyClickEvent(MouseEvent event) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"dashboardForm.fxml");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
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
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
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
    }


}

}
