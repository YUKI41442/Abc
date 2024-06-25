package org.example.bo.asset.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.bo.asset.ProductBo;
import org.example.dao.DaoFactory;
import org.example.dao.crud.impl.ProductDaoImpl;
import org.example.entity.ProductEntity;
import org.example.model.Product;

public class ProductBoImpl implements ProductBo {

    private final ProductDaoImpl productDaoImpl = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public void addProduct(Product product){
        productDaoImpl.insert(
                new ObjectMapper()
                        .convertValue(product, ProductEntity.class));
    }

    @Override
    public String generateProductId(){
        String id = productDaoImpl.getLatestId();

        if (id == null){
            return "P0001";
        }
        int number = Integer.parseInt(id.split("P")[1]);
        number++;
        return String.format("P%04d", number);
    }

    @Override
    public ObservableList<Product> getAllProducts(){
        ObservableList<ProductEntity> productEntities = productDaoImpl.getAll();
        ObservableList<Product> productsList = FXCollections.observableArrayList();

        productEntities.forEach(productEntity -> {
            productsList.add(
                    new ObjectMapper()
                            .convertValue(productEntity,Product.class));
        });

        return productsList;
    }

    @Override
    public Product getProductById(String id){
        return new ObjectMapper()
                .convertValue(productDaoImpl.search(id), Product.class);
    }

    @Override
    public boolean updateProduct(Product product){
        return productDaoImpl.update(
                new ObjectMapper()
                        .convertValue(product,ProductEntity.class));
    }

    @Override
    public boolean deleteProduct(String id){
        return productDaoImpl.delete(id);
    }

    @Override
    public ObservableList<String> getAllProductIds(){
        return productDaoImpl.getAllIds();
    }
}


