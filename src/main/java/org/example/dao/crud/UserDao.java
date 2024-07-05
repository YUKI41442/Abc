package org.example.dao.crud;

import org.example.dao.CrudDao;
import org.example.entity.UserEntity;

public interface UserDao extends CrudDao<UserEntity ,String> {
    UserEntity searchByEmail(String email);

    String getLatestId();

    boolean isUserPasswordMatches(String name, String password);

    boolean updatePasswordByEmail(String email,String password);
}
