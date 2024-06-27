package org.example.dao.crud;

import javafx.collections.ObservableList;
import org.example.entity.SupplierEntity;

public interface SupplierDao {
    SupplierEntity search(String id);

    ObservableList<SupplierEntity> getAll();

    void insert(SupplierEntity supplierEntity);

    boolean update(SupplierEntity supplierEntity);
    

    boolean delete(String id);

    String getLatestId();

    ObservableList<String> getAllIds();
}
