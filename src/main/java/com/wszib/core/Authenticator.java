package com.wszib.core;

import com.wszib.database.UserDAO;
import com.wszib.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

public class Authenticator {

    private final UserDAO userDB = UserDAO.getInstance();
    private static final Authenticator instance = new Authenticator();
    private Optional<User> loggedUser = Optional.empty();
    public final String seed = "12312312312312asdqw1@r";
    public Authenticator() {
    }
    public void authenticate(User user) {
        Optional<User> userFromDB = this.userDB.findByLogin(user.getLogin());
        if (userFromDB.isPresent() && userFromDB.get().getPassword().equals(DigestUtils.md5Hex(user.getPassword() + this.seed))) {
            this.loggedUser = userFromDB;
        }
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