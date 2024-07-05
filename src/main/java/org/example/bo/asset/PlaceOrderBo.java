package org.example.bo.asset;

import javafx.collections.ObservableList;
import org.example.bo.SuperBo;
import org.example.entity.OrderEntity;
import org.example.model.Order;

public interface PlaceOrderBo extends SuperBo {

    void saveOrder(Order order);

    String generateOrderId();

    ObservableList<OrderEntity> getAllOrders();

    boolean deleteOrderById(String id);

    Order getOrderById(String orderId);

    String getLatestOrderId();
}
