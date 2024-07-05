package org.example.bo;

import org.example.bo.asset.impl.*;
import org.example.util.BoType;

public class BoFactory {

    private static BoFactory instance;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return instance != null ? instance
                : (instance = new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case USER:return (T)new UserBoImpl();
            case CUSTOMER:return (T)new CustomerBoImpl();
            case PRODUCT:return (T)new ProductBoImpl();
            case PLACEORDER:return (T)new PlaceOrderBoImpl();
            case SUPPLIER:return (T)new SupplierBoImpl();
        }
        return null;
    }

}




