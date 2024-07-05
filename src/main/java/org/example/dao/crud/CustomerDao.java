package org.example.dao.crud;

import javafx.collections.ObservableList;
import org.example.dao.CrudDao;
import org.example.entity.CustomerEntity;

public interface CustomerDao extends CrudDao<CustomerEntity, String> {
    String getLatestId();

    ObservableList<CustomerEntity> getAllByEmpIds(String id);

}
