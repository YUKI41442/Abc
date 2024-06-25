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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierDetailsFormController implements Initializable {

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
    private TableColumn<?, ?> colSupplierCompany;

    @FXML
    private TableColumn<?, ?> colSupplierEmail;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private JFXTextField txtSupplierCompany;

    @FXML
    private JFXTextField txtSupplierEmail;

    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private AnchorPane supplierWindow;

    private final ScenseSwitchController sceneSwitch;

    private String currentId;

    private final SupplierBo supplierBo;

    public SupplierDetailsFromController() {
        this.sceneSwitch = ScenseSwitchController.getInstance();
        this.supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtSupplierId.setEditable(false);
        loadSupplierId();

        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSupplierEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSupplierCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        loadSupplierTbl();
    }

    private void clearTextFields(){
        txtSupplierName.setText("");
        txtSupplierEmail.setText("");
        txtSupplierCompany.setText("");
    }

    private void loadSupplierId(){
        currentId = supplierBo.generateSupplierId();
        txtSupplierId.setText(currentId);
    }

    private void loadSupplierTbl(){
        tblSupplier.setItems(supplierBo.getAllSuppliers());
    }


    @FXML
    void btnActionOnAction(ActionEvent event) {

        txtSupplierId.setEditable(true);
        txtSupplierId.setText("");
        btnAdd.setVisible(false);
        btnClear.setVisible(false);
        btnAction.setVisible(false);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        String email = txtSupplierEmail.getText();
        if (supplierBo.isValidEmail(email)){
            Supplier supplier = new Supplier(
                    currentId,
                    txtSupplierName.getText(),
                    email,
                    txtSupplierCompany.getText()
            );

            supplierBo.addSupplier(supplier);

            new Alert(Alert.AlertType.INFORMATION, "Supplier Added Successfully").show();

            clearTextFields();
            loadSupplierId();
            loadSupplierTbl();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Email. Try again...").show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearTextFields();
    }


    @FXML
    void btnCustomerDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(supplierWindow,"customerDetailsForm.fxml");
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(supplierWindow,"manageEmployeeForm.fxml");
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(supplierWindow,"orderDetailsForm.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(supplierWindow,"placeOrderForm.fxml");
    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(supplierWindow,"productDetailsForm.fxml");
    }

    @FXML
    void btnSuplierDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(supplierWindow,"supplierDetailsForm.fxml");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Supplier supplier = supplierBo.getSupplierById(txtSupplierId.getText());

        if (supplier != null){
            supplier.setName(txtSupplierName.getText());
            supplier.setEmail(txtSupplierEmail.getText());
            supplier.setCompany(txtSupplierCompany.getText());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            // Check if the response was OK or Cancel
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (supplierBo.updateSupplier(supplier)) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Update failed. Try again...").show();
                }
            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Supplier does not exists...").show();
        }
        loadSupplierTbl();
        clearTextFields();
    }
}

