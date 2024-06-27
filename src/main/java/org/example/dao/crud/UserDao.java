package org.example.dao.crud;

import javafx.collections.ObservableList;
import org.example.entity.UserEntity;

public interface UserDao {
    boolean isUserPasswordMatches(String name, String password);

    UserEntity search(String id);

    UserEntity searchByEmail(String email);

    ObservableList<UserEntity> getAll();
    

    boolean update(UserEntity userEntity);

    void insert(UserEntity userEntity);

    boolean delete(String id);

    boolean updatePasswordByEmail(String email, String password);

    String getLatestId();
}
