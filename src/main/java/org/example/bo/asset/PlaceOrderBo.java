package org.example.bo.asset;

import javafx.collections.ObservableList;

public interface PlaceOrderBo {
    String generateOrderId();

    ObservableList<OrderEntity> getAllOrders();

    void saveOrder(Order order);

    String getLatestOrderId();

    Order getOrderById(String orderId);

    void saveOrder(org.hibernate.query.Order order);

    boolean deleteOrderById(String id);
}
