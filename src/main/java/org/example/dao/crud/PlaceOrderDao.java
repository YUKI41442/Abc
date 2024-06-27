package org.example.dao.crud;

import javafx.collections.ObservableList;
import org.example.entity.OrderEntity;

public interface PlaceOrderDao {
    Object search(String s);

    ObservableList<OrderEntity> getAll();


    boolean update(OrderEntity orderEntity);

    void insert(OrderEntity orderEntity);

    boolean delete(String s);

    String getLatestOrderId();
}
