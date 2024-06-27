package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BoFactory;
import org.example.bo.asset.CustomerBo;
import org.example.model.Customer;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class CustomerDetailsFormController implements Initializable {

    public JFXButton btnSendOtp;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtReEnterPassword;
    public JFXButton btnReset;
    public JFXTextField txtOtp;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private TableColumn<?, ?> colCustomerAddress;

    @FXML
    private TableColumn<?, ?> colCustomerEmail;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private AnchorPane customerWindow;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerEmail;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtCustomerName;


    private String currentId;

    private final CustomerBo customerBo;

    private final ScenseSwitchController sceneSwitch;

    public CustomerDetailsFormController() {
        this.customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
        this.sceneSwitch = ScenseSwitchController.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtCustomerId.setEditable(false);
        loadCustomerId();

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadCustomerTbl();
    }

    @FXML
    void btnActionOnAction(ActionEvent event) {
        txtCustomerId.setEditable(true);
        txtCustomerId.setText("");
        btnAdd.setVisible(false);
        btnClear.setVisible(false);
        btnAction.setVisible(false);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        if (!areTextFieldsEmpty()) {
            String email = txtCustomerEmail.getText();
            if (customerBo.isValidEmail(email)){
                Customer customer = new Customer(
                        currentId,
                        txtCustomerName.getText(),
                        email,
                        txtCustomerAddress.getText()
                );

                customerBo.insertCustomer(customer);

                new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully").show();

                clearTextFields();
                loadCustomerId();
                loadCustomerTbl();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Email. Try again...").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }

    private boolean areTextFieldsEmpty() {
        return txtCustomerId.getText().isEmpty() &&
                txtCustomerName.getText().isEmpty() &&
                txtCustomerEmail.getText().isEmpty() &&
                txtCustomerAddress.getText().isEmpty();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearTextFields();
    }

    private void clearTextFields(){
        txtCustomerName.setText("");
        txtCustomerEmail.setText("");
        txtCustomerAddress.setText("");
    }

    private void loadCustomerId(){
        currentId = customerBo.generateCustomerId();
        txtCustomerId.setText(currentId);
    }

    private void loadCustomerTbl(){
        tblCustomer.setItems(customerBo.getAllCustomers());
    }


    @FXML
    void btnManageCustomersOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(customerWindow,"customerDetailsForm.fxml");
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(customerWindow,"manageEmployeeForm.fxml");
    }

    @FXML
    void btnManageOrderOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(customerWindow,"orderDetailsForm.fxml");
    }

    @FXML
    void btnManageProductsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(customerWindow,"productDetailsForm.fxml");
    }

    @FXML
    void btnManageSuppliersOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(customerWindow,"supplierDetailsForm.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(customerWindow,"placeOrderForm.fxml");
    }

}
