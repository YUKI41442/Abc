package org.example.bo.asset.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.bo.asset.CustomerBo;
import org.example.dao.DaoFactory;
import org.example.dao.crud.CustomerDao;
import org.example.entity.CustomerEntity;
import org.example.model.Customer;

public class CustomerBoImpl implements CustomerBo {

    private final CustomerDao customerDaoImpl;

    public CustomerBoImpl() {
        this.customerDaoImpl = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    }


    @Override
    public String generateCustomerId() {
        String lastCustomerId = customerDaoImpl.getLatestId();
        if (lastCustomerId == null){
            return "C0001";
        }
        int number = Integer.parseInt(lastCustomerId.split("C")[1]);
        number++;
        return String.format("C%04d", number);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerDaoImpl.insert(
                new ObjectMapper()
                        .convertValue(customer, CustomerEntity.class));
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<CustomerEntity> customerEntities = customerDaoImpl.getAll();

        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        customerEntities.forEach(customerEntity -> {
            customerList.add(
                    new ObjectMapper()
                            .convertValue(customerEntity, Customer.class));
        });
        return customerList;
    }

    @Override
    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public Customer getCustomerById(String id) {
        CustomerEntity customerEntity = customerDaoImpl.search(id);
        return new ObjectMapper().convertValue(customerEntity, Customer.class);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerDaoImpl.update(
                new ObjectMapper()
                        .convertValue(customer, CustomerEntity.class));
    }

    @Override
    public boolean deleteCustomerById(String id) {
        return customerDaoImpl.delete(id);
    }

    @Override
    public ObservableList<String> getAllCustomerIds() {
        ObservableList<CustomerEntity> customerEntities = customerDaoImpl.getAll();
        ObservableList<String> idList = FXCollections.observableArrayList();
        customerEntities.forEach(customerEntity -> {
            idList.add(customerEntity.getId());

        });
        return idList;
    }

    @Override
    public String generateOrderId() {
        return null;
    }

    @Override
    public void saveOrder(Order order) {

    }

    @Override
    public ObservableList<OrderEntity> getAllOrders() {
        return null;
    }

    @Override
    public void saveOrder(org.hibernate.query.Order order) {

    }

    @Override
    public String getLatestOrderId() {
        return null;
    }

    @Override
    public org.hibernate.query.Order getOrderById(String orderId) {
        return null;
    }

    @Override
    public boolean deleteOrderById(String id) {
        return false;
    }
}



