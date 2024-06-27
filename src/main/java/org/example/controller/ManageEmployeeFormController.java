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

    public Text lblClothify;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnAction;
    public JFXButton btnAdd;
    public JFXButton btnClear;
    
    public JFXButton btnCustomerDetails;
    
    public JFXButton btnManageEmployee;
    
    public JFXButton btnOrderDetails;
    
    public JFXButton btnPlaceOrder;

    public JFXButton btnProductDetails;
    
    public JFXButton btnSupplierDetails;
    
    public TableColumn<User, String> colEmployeeAddress;

    public TableColumn<User, String> colEmployeeEmail;

    public TableColumn<User, String> colEmployeeId;
    
    public TableColumn<User, String> colEmployeeName;
    
    public TableView<User> tblEmployee;
    
    public JFXTextField txtEmployeeAddress;
    
    public JFXTextField txtEmployeeEmail;

    public JFXTextField txtEmployeeId;

    public JFXTextField txtEmployeeName;
    
    public AnchorPane manageEmployeeWindow;

    private final UserBo userBo;
    

    private final ScenseSwitchController sceneSwitch;

    public ManageEmployeeFormController() {
        this.sceneSwitch = ScenseSwitchController.getInstance();
        this.userBo = BoFactory.getInstance().getBo(BoType.USER);
    }

   
    public void btnActionOnAction(ActionEvent event) {

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
        String currentId = userBo.generateEmployeeId();
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
                if (userBo.updateuser(user)) {
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
}


