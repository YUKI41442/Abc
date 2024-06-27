package org.example.dao.crud;

import javafx.collections.ObservableList;
import org.example.entity.ProductEntity;

public interface ProductDao {
    ProductEntity search(String s);

    ObservableList<ProductEntity> getAll();
    

    boolean update(ProductEntity productEntity);

    void insert(ProductEntity productEntity);

    boolean delete(String s);

    String getLatestId();

    ObservableList<String> getAllIds();

    boolean updateQtyById(String id, int qty);
}
