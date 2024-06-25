package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.entity.OrderEntity;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderDetailsFormController implements Initializable {

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
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableView<OrderEntity> tblOrder;

    @FXML
    private AnchorPane viewOrdersWindow;

    private final ScenseSwitchController sceneSwitch;

    private final PlaceOrderBo placeOrderBo;

    public OrderDetailsFromController() {
        this.sceneSwitch = ScenseSwitchController.getInstance();
        this.placeOrderBo = BoFactory.getInstance().getBo(BoType.PLACEORDER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tblOrder.setItems(placeOrderBo.getAllOrders());
    }


    @FXML
    void btnCustomerDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"customerDetailsForm.fxml");
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"manageEmployeeForm.fxml");
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"orderDetailsForm.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"placeOrderForm.fxml");
    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"productDetailsForm.fxml");
    }

    @FXML
    void btnSuplierDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"supplierDetailsForm.fxml");
    }
    @FXML
    void lblClothifyMouseClicked(MouseEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"dashboard-form.fxml");
    }

}


