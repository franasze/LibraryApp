package com.wszib.core;

import com.wszib.database.UserDAO;
import com.wszib.exceptions.IncorrectPasswordException;
import com.wszib.exceptions.UserNotFoundException;
import com.wszib.model.User;

import java.util.Optional;

public class Authenticator {

    private final UserDAO userDB = UserDAO.getInstance();
    private static final Authenticator instance = new Authenticator();
    private Optional<User> loggedUser = Optional.empty();
    private final String seed = "12312312312312asdqw1@r";

    private Authenticator() {
    }

    public void authenticate(User user) {
        Optional<User> userFromDB = this.userDB.findByLogin(user.getLogin());

        if (userFromDB.isEmpty())
            throw new UserNotFoundException();

        if (!userFromDB.get().getPassword().equals((user.getPassword())))
            throw new IncorrectPasswordException();

        this.loggedUser = userFromDB;
    }

    public Optional<User> getLoggedUser() {
        return loggedUser;
    }

    public String getSeed() {
        return seed;
    }

    public static Authenticator getInstance() {
        return instance;
    }

}