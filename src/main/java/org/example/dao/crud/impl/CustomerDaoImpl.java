package org.example.dao.crud.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.dao.crud.CustomerDao;
import org.example.entity.CustomerEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public CustomerEntity search(String s) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM customer WHERE id=:id");
        query.setParameter("id", s);
        CustomerEntity customerEntity = (CustomerEntity) query.uniqueResult();
        session.close();

        return customerEntity;
    }

    @Override
    public ObservableList<CustomerEntity> getAll() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        List<CustomerEntity> list = session.createQuery("FROM customer").list();
        session.close();

        ObservableList<CustomerEntity> customerEntityList = FXCollections.observableArrayList();
        customerEntityList.addAll(list);

        return customerEntityList;
    }

    @Override
    public void insert(CustomerEntity customerEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        session.persist(customerEntity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean update(CustomerEntity customerEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("UPDATE customer SET name=:name, address =:address, email =:email WHERE id =:id");
        query.setParameter("name",customerEntity.getName());
        query.setParameter("address",customerEntity.getAddress());
        query.setParameter("email",customerEntity.getEmail());
        query.setParameter("id",customerEntity.getId());

        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i > 0;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("DELETE FROM customer WHERE id=:id");
        query.setParameter("id",id);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i > 0;
    }

    @Override
    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();

        session.close();
        return id;
    }

    @Override
    public ObservableList<CustomerEntity> getAllByEmpIds(String id) {
        return null;
    }
}
