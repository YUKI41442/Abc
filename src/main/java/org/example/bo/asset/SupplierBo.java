package org.example.bo.asset;

import javafx.collections.ObservableList;

public interface SupplierBo {
    boolean isValidEmail(String email);

    String generateSupplierId();

    Supplier getSupplierById(String id);

    void addSupplier(Supplier supplier);

    ObservableList<Supplier> getAllSuppliers();

    boolean updateSupplier(Supplier supplier);

    boolean deleteSupplierById(String id);

    ObservableList<String> getAllSupplierIds();
}
