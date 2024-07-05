package org.example.bo.asset;

import javafx.collections.ObservableList;
import org.example.bo.SuperBo;
import org.example.model.Product;

public interface ProductBo extends SuperBo {
    void addProduct(Product product);

    String generateProductId();

    ObservableList<Product> getAllProducts();

    Product getProductById(String id);

    boolean deleteProduct(String id);

    boolean updateProduct(Product product);

    void updateQtyById(String id, int qty);

    ObservableList<String> getAllProductIds();
}
