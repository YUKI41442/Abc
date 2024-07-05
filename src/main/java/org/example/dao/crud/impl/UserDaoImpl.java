package org.example.dao.crud.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.dao.crud.UserDao;
import org.example.entity.UserEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao {

    public String getLatestId(){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM user ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }

    @Override
    public boolean isUserPasswordMatches(String name, String password) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery(
                "FROM user WHERE name =:name AND password =:password");
        query.setParameter("name", name);
        query.setParameter("password", password);
        UserEntity userEntity = (UserEntity) query.uniqueResult();
        session.close();

        return userEntity != null;
    }

    @Override
    public UserEntity search(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction();

        Query query = session.createQuery("FROM user WHERE id=:id");
        query.setParameter("id",id);
        UserEntity userEntity = (UserEntity) query.uniqueResult();

        session.close();
        return userEntity;
    }

    @Override
    public UserEntity searchByEmail(String email){

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM user WHERE email=:email");
        query.setParameter("email",email);
        UserEntity userEntity = (UserEntity) query.uniqueResult();
        session.close();
        return userEntity;
    }

    @Override
    public ObservableList<UserEntity> getAll(){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<UserEntity> list = session.createQuery("FROM user").list();
        ObservableList<UserEntity> userEntities= FXCollections.observableArrayList();
        session.close();
        userEntities.addAll(list);
        return userEntities;
    }

    @Override
    public void insert(UserEntity userEntity){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(userEntity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean update(UserEntity userEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("UPDATE user SET name =:name,address =:address,email =:email WHERE id =:id");
        query.setParameter("name",userEntity.getName());
        query.setParameter("address",userEntity.getAddress());
        query.setParameter("email",userEntity.getEmail());
        query.setParameter("id",userEntity.getId());

        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i > 0;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM user WHERE id=:id");
        query.setParameter("id",id);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return i > 0;
    }


    @Override
    public boolean updatePasswordByEmail(String email, String password) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("UPDATE user SET password =:p WHERE email =:e");
        query.setParameter("p",password);
        query.setParameter("e",email);
        int i = query.executeUpdate();

        session.getTransaction().commit();
        session.close();
        return i>0;
    }
}
