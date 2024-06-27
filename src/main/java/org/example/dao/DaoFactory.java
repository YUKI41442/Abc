package org.example.dao;

import org.example.dao.crud.impl.*;
import org.example.util.DaoType;

import static org.example.util.DaoType.*;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return instance != null ? instance
                : (instance = new DaoFactory());
    }

    public <T extends SuperDao>T getDao(DaoType type){
        if (type.equals(USER)) {
            return (T) new UserDaoImpl();
        } else if (type.equals(CUSTOMER)) {
            return (T) new CustomerDaoImpl();
        } else if (type.equals(PRODUCT)) {
            return (T) new ProductDaoImpl();
        } else if (type.equals(CART)) {
            return (T) new PlaceOrderDaoImpl();
        } else if (type.equals(ORDER)) {
            return (T) new PlaceOrderDaoImpl();
        } else if (type.equals(SUPPLIER)) {
            return (T) new SupplierDaoImpl();
        }
        return null;
    }

}