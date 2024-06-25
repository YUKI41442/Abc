package org.example.bo.asset;

import javafx.collections.ObservableList;

public interface CustomerBo {
    String generateCustomerId();

    void insertCustomer(Customer customer);

    ObservableList<Customer> getAllCustomers();

    boolean isValidEmail(String email);

    Customer getCustomerById(String id);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomerById(String id);

    ObservableList<String> getAllCustomerIds();

    String generateOrderId();

    void saveOrder(Order order);

    ObservableList<OrderEntity> getAllOrders();

    void saveOrder(org.hibernate.query.Order order);

    String getLatestOrderId();

    org.hibernate.query.Order getOrderById(String orderId);

    boolean deleteOrderById(String id);
}
