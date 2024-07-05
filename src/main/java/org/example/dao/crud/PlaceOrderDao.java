package org.example.dao.crud;

import org.example.dao.CrudDao;
import org.example.entity.OrderEntity;

public interface PlaceOrderDao extends CrudDao<OrderEntity,String> {
    String getLatestOrderId();

}
