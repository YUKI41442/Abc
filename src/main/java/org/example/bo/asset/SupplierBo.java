package org.example.bo.asset;

import javafx.collections.ObservableList;
import org.example.bo.SuperBo;
import org.example.model.Supplier;

public interface SupplierBo extends SuperBo {
    boolean isValidEmail(String email);

    String generateSupplierId();

    void addSupplier(Supplier supplier);

    ObservableList<Supplier> getAllSuppliers();

    boolean updateSupplier(Supplier supplier);

    boolean deleteSupplierById(String id);

    ObservableList<String> getAllSupplierIds();

    Supplier getSupplierById(String id);
}
