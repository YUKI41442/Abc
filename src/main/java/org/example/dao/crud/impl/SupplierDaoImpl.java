package org.example.dao.crud.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.dao.crud.SupplierDao;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public SupplierEntity search(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction();

        Query query = session.createQuery("FROM supplier WHERE id=:id");
        query.setParameter("id",id);
        SupplierEntity supplierEntity = (SupplierEntity) query.uniqueResult();
        session.close();
        return supplierEntity;
    }

    @Override
    public ObservableList<SupplierEntity> getAll() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<SupplierEntity> list = session.createQuery("FROM supplier").list();
        session.close();
        ObservableList<SupplierEntity> supplierEntityList = FXCollections.observableArrayList();
        supplierEntityList.addAll(list);
        return supplierEntityList;
    }

    @Override
    public void insert(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(supplierEntity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean update(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("UPDATE supplier SET name=:name ,email=:email ,company=:company WHERE id=:id");
        query.setParameter("name",supplierEntity.getName());
        query.setParameter("email",supplierEntity.getEmail());
        query.setParameter("company",supplierEntity.getCompany());
        query.setParameter("id",supplierEntity.getId());
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return i > 0;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM supplier WHERE id=:id");
        query.setParameter("id",id);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return i > 0;
    }

    @Override
    public String getLatestId(){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM supplier ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }

    public ObservableList<String> getAllIds(){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery("SELECT id FROM supplier").list();
        session.close();

        ObservableList<String> idList = FXCollections.observableArrayList();
        idList.addAll(list);
        return idList;
    }
}
