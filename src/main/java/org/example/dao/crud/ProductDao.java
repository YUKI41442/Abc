package org.example.dao.crud;

import javafx.collections.ObservableList;

public interface ProductDao {
    ProductEntity search(String s);

    ObservableList<ProductEntity> getAll();

    void insert(ProductEntity productEntity);

    boolean update(ProductEntity productEntity);

    boolean delete(String s);

    String getLatestId();

    ObservableList<String> getAllIds();
}
