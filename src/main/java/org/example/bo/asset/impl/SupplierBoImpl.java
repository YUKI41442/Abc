package org.example.bo.asset.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.bo.asset.SupplierBo;
import org.example.dao.DaoFactory;
import org.example.dao.crud.SupplierDao;
import org.example.entity.SupplierEntity;
import org.example.model.Supplier;
import org.example.util.DaoType;

public class SupplierBoImpl implements SupplierBo {

    private final SupplierDao supplierDao;

    public SupplierBoImpl() {
        this.supplierDao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);
    }

    @Override
    public boolean isValidEmail(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public String generateSupplierId(){
        String lastSupplierId = supplierDao.getLatestId();

        if (lastSupplierId==null){
            return "S0001";
        }

        int number = Integer.parseInt(lastSupplierId.split("S")[1]);
        number++;
        return String.format("S%04d", number);
    }

    @Override
    public Supplier getSupplierById(String id){
        return new ObjectMapper()
                .convertValue(supplierDao.search(id), Supplier.class);
    }

    @Override
    public void addSupplier(Supplier supplier){
        supplierDao.insert(
                new ObjectMapper()
                        .convertValue(supplier, SupplierEntity.class));
    }

    @Override
    public ObservableList<Supplier> getAllSuppliers(){
        ObservableList<SupplierEntity> supplierEntities = supplierDao.getAll();
        ObservableList<Supplier> list = FXCollections.observableArrayList();

        supplierEntities.forEach(supplierEntity -> {
            list.add(
                    new ObjectMapper()
                            .convertValue(supplierEntity, Supplier.class));
        });
        return list;
    }

    @Override
    public boolean updateSupplier(Supplier supplier){
        return supplierDao.update(
                new ObjectMapper()
                        .convertValue(supplier, SupplierEntity.class));
    }

    @Override
    public boolean deleteSupplierById(String id){
        return supplierDao.delete(id);
    }

    @Override
    public ObservableList<String> getAllSupplierIds(){
        return supplierDao.getAllIds();
    }
}
