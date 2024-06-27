package org.example.bo;

import org.example.bo.asset.impl.*;
import org.example.util.BoType;

import static org.example.util.BoType.*;

public class BoFactory {

    private static BoFactory instance;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return instance != null ? instance
                : (instance = new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        if (type.equals(USER)) {
            return (T) new UserBoImpl();
        } else if (type.equals(CUSTOMER)) {
            return (T) new CustomerBoImpl();
        } else if (type.equals(PRODUCT)) {
            return (T) new ProductBoImpl();
        } else if (type.equals(PLACEORDER)) {
            return (T) new PlaceOrderBoImpl();
        } else if (type.equals(SUPPLIER)) {
            return (T) new SupplierBoImpl();
        }
        return null;
    }
}




