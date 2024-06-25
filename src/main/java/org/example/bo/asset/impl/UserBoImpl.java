package org.example.bo.asset.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.bo.asset.UserBo;

public class UserBoImpl implements UserBo {

    private final UserDaoImpl userDaoImpl = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public void insertUser(User user){
        userDaoImpl.insert(
                new ObjectMapper()
                        .convertValue(user, UserEntity.class));
    }

    @Override
    public UserEntity getUserByEmail(String email){
        return userDaoImpl.searchByEmail(email);
    }

    @Override
    public boolean isValidEmail(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public ObservableList<String> getAllUserIds(){
        ObservableList<UserEntity> list = userDaoImpl.getAll();

        ObservableList<String> idList = FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            idList.add(userEntity.getId());
        });
        return idList;
    }

    @Override
    public User getUserById(String id){
        return new ObjectMapper()
                .convertValue(userDaoImpl.search(id), User.class);
    }

    @Override
    public ObservableList<User> getAllUsers(){
        ObservableList<UserEntity> list = userDaoImpl.getAll();
        ObservableList<User> userList = FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            userList.add(
                    new ObjectMapper()
                            .convertValue(userEntity,User.class));
        });
        return userList;
    }

    @Override
    public boolean updateUser(User user){
        return userDaoImpl.update(
                new ObjectMapper()
                        .convertValue(user, UserEntity.class));
    }

    @Override
    public boolean deleteUserById(String id){
        return userDaoImpl.delete(id);
    }

    public String generateEmployeeId(){
        String lastEmployeeId = userDaoImpl.getLatestId();
        if (lastEmployeeId == null){
            return "U0001";
        }

        int number = Integer.parseInt(lastEmployeeId.split("U")[1]);
        number++;
        return String.format("U%04d", number);
    }
}


