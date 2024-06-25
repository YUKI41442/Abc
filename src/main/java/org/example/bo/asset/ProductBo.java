package org.example.bo.asset;

import javafx.collections.ObservableList;
import org.example.model.Product;

public interface ProductBo {
    void addProduct(Product product);

    void addProduct(Product product);

    String generateProductId();

    ObservableList<Product> getAllProducts();

    Product getProductById(String id);

    boolean updateProduct(Product product);

    boolean deleteProduct(String id);

    ObservableList<String> getAllProductIds();
}
