package org.example.dao.crud.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.dao.crud.PlaceOrderDao;
import org.example.entity.OrderEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PlaceOrderDaoImpl implements PlaceOrderDao {

    @Override
    public Object search(String s) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM order_table WHERE id=:id");
        query.setParameter("id",s);

        return (OrderEntity)query.uniqueResult();
    }

    @Override
    public ObservableList<OrderEntity> getAll() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<OrderEntity> list = session.createQuery("FROM order_table").list();
        session.close();

        ObservableList<OrderEntity> orderEntities = FXCollections.observableArrayList();
        orderEntities.addAll(list);

        return orderEntities;
    }

    @Override
    public void insert(OrderEntity orderEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        session.persist(orderEntity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean update(OrderEntity orderEntity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("DELETE FROM order_table WHERE id=:id");
        query.setParameter("id",s);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i > 0;
    }

    @Override
    public String getLatestOrderId(){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM order_table ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();

        session.close();

        return id;
    }
}
