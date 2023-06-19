package com.wszib.core;

import com.wszib.database.UserDAO;
import com.wszib.model.User;

public class Authenticator {

    private final UserDAO userDB = UserDAO.getInstance();
    private static final Authenticator instance = new Authenticator();
    public User loggedUser = null;
    public final String seed = "12312312312312asdqw1@r";

    public Authenticator() {

    }

    public void authenticate(User user) {
        try {
            loggedUser = null;
            User userFromDB = this.userDB.findByLogin(user.getLogin());
            if (userFromDB != null &&
                    userFromDB.getPassword().equals(user.getPassword())) {
                loggedUser = userFromDB;
            }
        }catch (NullPointerException e){
            System.out.println("Wrong login or password");
        }
    }


    public String getSeed(){
        return seed;
    }

    public static Authenticator getInstance() {
        return instance;
    }

}