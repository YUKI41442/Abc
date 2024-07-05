package org.example.dao.crud;

import javafx.collections.ObservableList;
import org.example.dao.CrudDao;
import org.example.entity.SupplierEntity;

public interface SupplierDao extends CrudDao<SupplierEntity, String > {
    String getLatestId();

    ObservableList<String> getAllIds();
}
