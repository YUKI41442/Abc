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
import org.example.bo.asset.SupplierBo;
import org.example.model.Supplier;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierDetailsFormController implements Initializable {

    @FXML
    public JFXButton btnAction;

    @FXML
    public JFXButton btnAdd;

    @FXML
    public JFXButton btnClear;

    @FXML
    public JFXButton btnCustomerDetails;

    @FXML
    public JFXButton btnManageEmployee;

    @FXML
    public JFXButton btnOrderDetails;

    @FXML
    public JFXButton btnPlaceOrder;

    @FXML
    public JFXButton btnProductDetails;

    @FXML
    public JFXButton btnSupplierDetails;

    @FXML
    public TableColumn<?, ?> colSupplierCompany;

    @FXML
    public TableColumn<?, ?> colSupplierEmail;

    @FXML
    public TableColumn<?, ?> colSupplierId;

    @FXML
    public TableColumn<?, ?> colSupplierName;

    @FXML
    public Text lblClothify;

    @FXML
    public AnchorPane supplierWindow;

    @FXML
    public TableView<Supplier> tblSupplier;

    @FXML
    public JFXTextField txtSupplierCompany;

    @FXML
    public JFXTextField txtSupplierEmail;

    @FXML
    public JFXTextField txtSupplierId;

    @FXML
    public JFXTextField txtSupplierName;

    private final ScenseSwitchController sceneSwitch;

    private String currentId;

    private final SupplierBo supplierBo;

    public SupplierDetailsFormController() {
        this.sceneSwitch = ScenseSwitchController.getInstance();
        this.supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
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

        if (!areTextFieldsEmpty()) {
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
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!areTextFieldsEmpty()) {
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
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
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
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(supplierWindow,"manageEmployeeForm.fxml");
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(supplierWindow,"orderDetailsForm.fxml");

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(supplierWindow,"placeOrderForm.fxml");
    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(supplierWindow,"productDetailsForm.fxml");

    }
    @FXML
    void btnSuplierDetailsOnAction(ActionEvent event) throws IOException {

        sceneSwitch.switchScene(supplierWindow,"supplierDetailsForm.fxml");

    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (!areTextFieldsEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            // Check if the response was OK or Cancel
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (supplierBo.deleteSupplierById(txtSupplierId.getText())) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted Successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Delete failed. Try again...").show();
                }
            }
            loadSupplierTbl();
            clearTextFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
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

    private boolean areTextFieldsEmpty() {
        return txtSupplierId.getText().isEmpty() &&
                txtSupplierName.getText().isEmpty() &&
                txtSupplierCompany.getText().isEmpty() &&
                txtSupplierEmail.getText().isEmpty();
    }
}
