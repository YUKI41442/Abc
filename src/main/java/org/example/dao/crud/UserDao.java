package org.example.dao.crud;

import javafx.collections.ObservableList;

public interface UserDao {
    boolean isUserPasswordMatches(String name, String password);

    UserEntity search(String id);

    UserEntity searchByEmail(String email);

    ObservableList<UserEntity> getAll();

    void insert(UserEntity userEntity);

    boolean update(UserEntity userEntity);

    boolean delete(String id);
}
