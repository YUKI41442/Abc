package org.example.bo.asset;

import javafx.collections.ObservableList;

public interface UserBo {
    void insertUser(User user);

    UserEntity getUserByEmail(String email);

    boolean isValidEmail(String email);

    ObservableList<String> getAllUserIds();

    User getUserById(String id);

    ObservableList<User> getAllUsers();

    boolean updateUser(User user);

    boolean deleteUserById(String id);
}
