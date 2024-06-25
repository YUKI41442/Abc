package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private JFXButton btnCustomerDetails;

    @FXML
    private JFXButton btnFinalizeOrder;

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
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Text lblCashier;

    @FXML
    private Text lblTotal;

    @FXML
    private AnchorPane placeOrderWindow;

    @FXML
    private TableView<Cart> tblCart;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerEmail;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtItemQty;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private JFXTextField txtPrice;

    private final ScenseSwitchController sceneSwitch;

    private final ProductBo productBo;

    private final CustomerBo customerBo;

    private final PlaceOrderBo placeOrderBo;

    private int cartId;

    private double total;

    private final ObservableList<Cart> cartList = FXCollections.observableArrayList();

    public PlaceOrderFormController() {
        this.sceneSwitch = ScenseSwitchController.getInstance();
        this.productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
        this.customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
        this.placeOrderBo = BoFactory.getInstance().getBo(BoType.PLACEORDER);
        this.cartId = 1;
        this.total = 0;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateTime();
        txtOrderId.setEditable(false);
        txtCustomerName.setEditable(false);
        txtCustomerEmail.setEditable(false);
        txtCustomerAddress.setEditable(false);
        txtItemName.setEditable(false);
        txtPrice.setEditable(false);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cmbCustomerId.setItems(customerBo.getAllCustomerIds());
        cmbItemId.setItems(productBo.getAllProductIds());

        txtOrderId.setText(placeOrderBo.generateOrderId());
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        double unitPrice = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtItemQty.getText());
        total += (unitPrice * qty);
        Cart cart = new Cart(
                cartId++,
                txtItemName.getText(),
                unitPrice,
                qty,
                (unitPrice * qty)
        );
        lblTotal.setText(String.valueOf(total));
        cartList.add(cart);
        tblCart.setItems(cartList);
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        cartId = 1;
        lblTotal.setText("0.00");
        Customer customer = customerBo.getCustomerById((String) cmbCustomerId.getValue());
        txtCustomerName.setText(customer.getName());
        txtCustomerEmail.setText(customer.getEmail());
        txtCustomerAddress.setText(customer.getAddress());
        tblCart.getItems().clear();
    }

    @FXML
    void cmbItemIdOnAction(ActionEvent event) {
        Product product = productBo.getProductById((String) cmbItemId.getValue());
        txtItemName.setText(product.getName());
        txtPrice.setText(String.valueOf(product.getPrice()));
    }

    @FXML
    void btnCustomerDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(placeOrderWindow,"customer-details-form.fxml");
    }

    @FXML
    void btnFinalizeOderOnAction(ActionEvent event) {

        System.out.println(cmbCustomerId.getValue());
        Order order = new Order(
                txtOrderId.getText(),
                cmbCustomerId.getValue(),
                new Date(),
                Double.parseDouble(lblTotal.getText())
        );

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Finalize order?");
        Optional<ButtonType> result = alert.showAndWait();
        // Check if the response was OK or Cancel
        if (result.isPresent() && result.get() == ButtonType.OK) {
            placeOrderBo.saveOrder(order);
            new Alert(Alert.AlertType.INFORMATION, "Order placed Successfully").show();
        }
    }

    private void loadDateTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(format.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            lblTime.setText(
                    localTime.getHour()+" : "+localTime.getMinute()+" : "+localTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(placeOrderWindow,"manageEmployeeForm.fxml");
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(placeOrderWindow,"orderDetailsForm.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(placeOrderWindow,"placeOrderForm.fxml");
    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(placeOrderWindow,"productDetailsForm.fxml");
    }

    @FXML
    void btnSuplierDetailsOnAction(ActionEvent event) throws IOException{
        sceneSwitch.switchScene(placeOrderWindow,"supplierDetailsForm.fxml");
    }
}


