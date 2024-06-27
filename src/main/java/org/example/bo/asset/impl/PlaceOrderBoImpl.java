package org.example.bo.asset.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import org.example.bo.asset.PlaceOrderBo;
import org.example.dao.DaoFactory;
import org.example.dao.crud.PlaceOrderDao;
import org.example.dao.crud.impl.PlaceOrderDaoImpl;
import org.example.entity.OrderEntity;
import org.example.model.Order;
import org.example.util.DaoType;

public class PlaceOrderBoImpl implements PlaceOrderBo {

    private final PlaceOrderDao placeOrderDao;

    public PlaceOrderBoImpl() {
        this.placeOrderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);
    }

    @Override
    public String generateOrderId(){
        String id = new PlaceOrderDaoImpl().getLatestOrderId();

        if (id == null){
            return "B0001";
        }
        int number = Integer.parseInt(id.split("B")[1]);
        number++;
        return String.format("B%04d", number);
    }

    @Override
    public ObservableList<OrderEntity> getAllOrders(){
        return placeOrderDao.getAll();
    }

    @Override
    public void saveOrder(Order order){
        new PlaceOrderDaoImpl().insert(
                new ObjectMapper().convertValue(order, OrderEntity.class));
    }

    @Override
    public String getLatestOrderId(){
        return placeOrderDao.getLatestOrderId();
    }

    @Override
    public Order getOrderById(String orderId){
        return new ObjectMapper()
                .convertValue(placeOrderDao.search(orderId), Order.class);
    }

    @Override
    public boolean deleteOrderById(String id){
        return placeOrderDao.delete(id);
    }
}