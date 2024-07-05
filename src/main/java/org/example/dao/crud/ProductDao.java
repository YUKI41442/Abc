package org.example.dao.crud;

import javafx.collections.ObservableList;
import org.example.dao.CrudDao;
import org.example.entity.ProductEntity;

public interface ProductDao extends CrudDao<ProductEntity,String> {
    String getLatestId();

    ObservableList<String> getAllIds();

    boolean updateQtyById(String id, int qty);
}

