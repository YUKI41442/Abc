package org.example.bo.asset;

import javafx.collections.ObservableList;
import org.example.bo.SuperBo;
import org.example.model.Customer;

public interface CustomerBo extends SuperBo {
    String generateCustomerId();

    void insertCustomer(Customer customer);

    ObservableList<Customer> getAllCustomers();

    boolean isValidEmail(String email);

    Customer getCustomerById(String id);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomerById(String id);

    ObservableList<String> getAllCustomerIds();
}
