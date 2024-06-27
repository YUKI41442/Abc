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
import org.example.bo.BoFactory;
import org.example.bo.asset.ProductBo;
import org.example.model.Product;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductDetailsFormController implements Initializable {

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
    private TableColumn<?, ?> colProductCategory;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colProductPrice;

    @FXML
    private TableColumn<?, ?> colProductQty;

    @FXML
    private TableView<Product> tblProduct;

    @FXML
    private JFXTextField txtCategory;

    @FXML
    private JFXTextField txtItemId;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private AnchorPane productsWindow;

    private final ProductBo productBo;

    private String currentId;

    private final ScenseSwitchController sceneSwitch;

    public ProductDetailsFormController() {
        this.productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
        this.sceneSwitch = ScenseSwitchController.getInstance();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtItemId.setEditable(false);
        loadProductId();

        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colProductCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadProductTbl();
    }

    private void clearTextFields(){
        txtItemName.setText("");
        txtQty.setText("");
        txtCategory.setText("");
        txtPrice.setText("");
    }

    private void loadProductId(){
        currentId = productBo.generateProductId();
        txtItemId.setText(currentId);
    }

    private void loadProductTbl() { tblProduct.setItems(productBo.getAllProducts());}

    @FXML
    void btnActionOnAction(ActionEvent event) {

        txtItemId.setEditable(true);
        txtItemId.setText("");
        btnAdd.setVisible(false);
        btnClear.setVisible(false);
        btnAction.setVisible(false);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        Product product = new Product(
                currentId,
                txtItemName.getText(),
                Integer.parseInt(txtQty.getText()),
                txtCategory.getText(),
                Double.parseDouble(txtPrice.getText())
        );

        productBo.addProduct(product);

        new Alert(Alert.AlertType.INFORMATION, "Product Added Successfully").show();

        clearTextFields();
        loadProductId();
        loadProductTbl();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearTextFields();
    }

    @FXML
    void btnCustomerDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(productsWindow,"customerDetailsForm.fxml");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?");
        Optional<ButtonType> result = alert.showAndWait();
        // Check if the response was OK or Cancel
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (productBo.deleteProduct(txtItemId.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Product Deleted Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Delete failed. Try again...").show();
            }
        }
        loadProductTbl();
        clearTextFields();
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(productsWindow,"manageEmployeeForm.fxml");
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(productsWindow,"orderDetailsForm.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(productsWindow,"placeOrderForm.fxml");
    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(productsWindow,"productDetailsForm.fxml");
    }

    @FXML
    void btnSuplierDetailsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(productsWindow,"supplierDetailsForm.fxml");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Product product = productBo.getProductById(txtItemId.getText());

        if (product != null){
            product.setName(txtItemName.getText());
            product.setQty(Integer.parseInt(txtQty.getText()));
            product.setCategory(txtCategory.getText());
            product.setPrice(Double.parseDouble(txtPrice.getText()));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            // Check if the response was OK or Cancel
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (productBo.updateProduct(product)) {
                    new Alert(Alert.AlertType.INFORMATION, "Product Updated Successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Update failed. Try again...").show();
                }
            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Product does not exists...").show();
        }
        loadProductTbl();
        clearTextFields();
    }

    @FXML
    void lblCLothifyMouseClicked(MouseEvent event) throws IOException {
        sceneSwitch.switchScene(productsWindow,"dashboardForm.fxml");
    }
}

