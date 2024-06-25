package org.example.dao.crud;

import javafx.collections.ObservableList;

public interface PlaceOrderDao {
    OrderEntity search(String s);

    ObservableList<OrderEntity> getAll();

    void insert(OrderEntity orderEntity);

    boolean update(OrderEntity orderEntity);

    boolean delete(String s);

    String getLatestOrderId();
}
