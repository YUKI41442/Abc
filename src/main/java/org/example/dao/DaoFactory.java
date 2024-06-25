package org.example.dao;

import org.example.util.DaoType;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return instance != null ? instance
                : (instance = new DaoFactory());
    }

    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case USER:return (T)new UserDaoImpl();
            case CUSTOMER:return (T)new CustomerDaoImpl();
            case PRODUCT:return (T)new ProductDaoImpl();
            case CART:return (T)new PlaceOrderDaoImpl();
            case ORDER:return (T)new PlaceOrderDaoImpl();
            case SUPPLIER:return (T)new SupplierDaoImpl();
        }
        return null;
    }

}