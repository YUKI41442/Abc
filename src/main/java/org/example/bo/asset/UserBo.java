package org.example.bo.asset;

import javafx.collections.ObservableList;
import org.example.bo.SuperBo;
import org.example.entity.UserEntity;
import org.example.model.User;

import javax.mail.MessagingException;

public interface UserBo extends SuperBo {
    UserEntity getUserByEmail(String email);

    void insertUser(User user);

    boolean isValidEmail(String email);

    ObservableList<String> getAllUserIds();

    User getUserById(String id);

    ObservableList<User> getAllUsers();

    boolean updateUser(User user);

    boolean updateuser(User user);

    boolean deleteUserById(String id);

    String generateEmployeeId();

    boolean checkIfUserPasswordMatches(String name, String password);

    String passwordEncrypt(String password);

    String passwordDecrypt(String password);

    void sendEmail(String receiveEmail, String text) throws MessagingException;

    boolean updatePasswordByEmail(String email, String password);

}