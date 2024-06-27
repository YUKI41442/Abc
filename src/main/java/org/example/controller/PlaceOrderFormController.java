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
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.bo.BoFactory;
import org.example.bo.asset.CustomerBo;
import org.example.bo.asset.PlaceOrderBo;
import org.example.bo.asset.ProductBo;
import org.example.model.Cart;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.model.Product;
import org.example.util.BoType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class PlaceOrderFormController implements Initializable {

    public JFXTextField txtAvailableQty;
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

    private final Map<String, Integer> map;

    private final ObservableList<Cart> cartList = FXCollections.observableArrayList();

    public PlaceOrderFormController(Text lblCashier) {
        this.lblCashier = lblCashier;
        this.sceneSwitch = ScenseSwitchController.getInstance();
        this.productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
        this.customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
        this.placeOrderBo = BoFactory.getInstance().getBo(BoType.PLACEORDER);
        this.map = new HashMap<>();
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
        txtAvailableQty.setEditable(false);

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
        if (!txtItemQty.getText().isEmpty()) {
            Product product = productBo.getProductById(cmbItemId.getValue());
            double unitPrice = Double.parseDouble(txtPrice.getText());
            int qty = Integer.parseInt(txtItemQty.getText());
            total += (unitPrice * qty);

            map.put(product.getId(), product.getQty() - qty);
            txtAvailableQty.setText(String.valueOf(product.getQty() - qty));

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
        } else {
            new Alert(Alert.AlertType.ERROR, "Quantity field can't be empty!").show();
        }

    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        cartId = 1;
        lblTotal.setText("0.00");
        Customer customer = customerBo.getCustomerById(cmbCustomerId.getValue());
        txtCustomerName.setText(customer.getName());
        txtCustomerEmail.setText(customer.getEmail());
        txtCustomerAddress.setText(customer.getAddress());
        tblCart.getItems().clear();
        map.clear();
        cartList.clear();
    }

    @FXML
    void cmbItemIdOnAction(ActionEvent event) {
        Product product = productBo.getProductById(cmbItemId.getValue());
        txtItemName.setText(product.getName());
        txtPrice.setText(String.valueOf(product.getPrice()));
        txtAvailableQty.setText(String.valueOf(product.getQty()));
    }


    @FXML
    void txtQtyKeyTypedEvent(KeyEvent event) {
    }

    @FXML
    void btnCustomerDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(placeOrderWindow,"customer-details-form.fxml");
    }

    @FXML
    void btnFinalizeOrderOnAction(ActionEvent event) throws JRException, IOException {
        if (!areTextFieldsEmpty()) {
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

                map.forEach(productBo::updateQtyById);

                new Alert(Alert.AlertType.INFORMATION, "Order placed Successfully").show();

                //generateInvoice();

                tblCart.getItems().clear();
                map.clear();
                clearTextFields();
                lblTotal.setText("0.00");
                cartId = 1;
                cartList.clear();

                txtOrderId.setText(placeOrderBo.generateOrderId());
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }

    private void generateInvoice() throws JRException, IOException {
        Map<String, Object> parameters = new HashMap<>();

        String path = "D:\\ColthifyStore\\src\\main\\resources\\report\\Invoice_Table_Based.jrxml";
        JasperReport jasperReport = JasperCompileManager.compileReport(path);

        String pdfPath = "D:\\ColthifyStore\\src\\main\\resources\\reportPdf\\" + txtOrderId.getText() + ".pdf";

        parameters.put("customerId", cmbCustomerId.getValue());
        parameters.put("customerName", txtCustomerName.getText());
        parameters.put("customerEmail", txtCustomerEmail.getText());
        parameters.put("customerAddress", txtCustomerAddress.getText());

        parameters.put("orderId", txtOrderId.getText());
        parameters.put("orderTotal", Double.parseDouble(lblTotal.getText()));

        //parameters.put("invoiceId", "INV" + txtOrderId.getText());

        //List<Cart> list = new ArrayList<>(cartList);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cartList);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);

        File file = new File(pdfPath);

        if (file.exists() && Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        } else {
            new Alert(Alert.AlertType.ERROR, "Report not found").show();
        }
    }

    private void loadDateTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(format.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            lblTime.setText(
                    localTime.getHour() + " : " + localTime.getMinute() + " : " + localTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(placeOrderWindow,"manage-employee-form.fxml");
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(placeOrderWindow,"order-details-form.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(placeOrderWindow,"place-order-form.fxml");
    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(placeOrderWindow,"product-details-form.fxml");
    }

    @FXML
    void btnSupplierDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(placeOrderWindow,"supplier-details-form.fxml");
    }

    @FXML
    void lblClothifyMouseClicked(MouseEvent event) throws IOException {
        sceneSwitch.switchScene(placeOrderWindow,"dashboard-form.fxml");
    }

    private boolean areTextFieldsEmpty() {
        return txtCustomerName.getText().isEmpty() &&
                txtCustomerEmail.getText().isEmpty() &&
                txtCustomerAddress.getText().isEmpty() &&
                txtItemName.getText().isEmpty() &&
                txtItemQty.getText().isEmpty() &&
                txtPrice.getText().isEmpty();
    }

    private void clearTextFields() {
        txtCustomerName.clear();
        txtCustomerEmail.clear();
        txtCustomerAddress.clear();
        txtItemName.clear();
        txtItemQty.clear();
        txtAvailableQty.clear();
        txtPrice.clear();
    }

    public void btnFinalizeOderOnAction(ActionEvent actionEvent) {
    }

    public void btnSuplierDetailsOnAction(ActionEvent actionEvent) {
    }
}


