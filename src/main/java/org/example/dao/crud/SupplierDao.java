package org.example.dao.crud;

import javafx.collections.ObservableList;

public interface SupplierDao {
    SupplierEntity search(String id);

    ObservableList<SupplierEntity> getAll();

    void insert(SupplierEntity supplierEntity);

    boolean update(SupplierEntity supplierEntity);

    boolean delete(String id);

    String getLatestId();
}
