package org.example.dao.crud;

import javafx.collections.ObservableList;
import org.example.entity.CustomerEntity;

public interface CustomerDao {
    CustomerEntity search(String s);

    ObservableList<CustomerEntity> getAll();

    void insert(CustomerEntity customerEntity);

    boolean update(CustomerEntity customerEntity);


    boolean delete(String id);

    String getLatestId();

    ObservableList<CustomerEntity> getAllByEmpIds(String id);
}
