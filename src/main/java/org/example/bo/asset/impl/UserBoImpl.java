package org.example.bo.asset.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.bo.asset.UserBo;
import org.example.dao.DaoFactory;
import org.example.dao.crud.UserDao;
import org.example.entity.UserEntity;
import org.example.model.User;
import org.example.util.DaoType;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserBoImpl implements UserBo {

    private final UserDao userDaoImpl;

    public UserBoImpl() {
        this.userDaoImpl = DaoFactory.getInstance().getDao(DaoType.USER);
    }

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

    public boolean updatePasswordByEmail(String email,String password){
        return userDaoImpl.updatePasswordByEmail(email, password);
    }
    
    @Override
    public boolean updateuser(User user) {
        return false;
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

    @Override
    public boolean checkIfUserPasswordMatches(String name, String password) {
        return userDaoImpl.isUserPasswordMatches(name, passwordEncrypt(password));
    }

    @Override
    public String passwordEncrypt(String password){
        return new String(Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String passwordDecrypt(String password){
        return new String(Base64.getDecoder().decode(password));
    }

    @Override
    public void sendEmail(String receiveEmail, String text) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myEmail = "rasangasampath1111@gmail.com";
        String password = "rasanga";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });

        Message message = prepareMessage(session,myEmail,receiveEmail,text);
        Transport.send(message);
    }

    public Message prepareMessage(Session session, String myEmail, String receiveEmail, String text) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO,new InternetAddress[]{
                    new InternetAddress(receiveEmail)
            });
            message.setSubject("OTP CODE");
            message.setText(text);

            return message;
        }catch (Exception e){
            //Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
    }

    public boolean passwordValidate(String password){
        Pattern pattern = Pattern.compile("((?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#%$!&]).{8,20})");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}

