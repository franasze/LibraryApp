package com.wszib.core;

import com.wszib.database.BookDAO;
import com.wszib.database.UserDAO;
import com.wszib.gui.GUI;
import com.wszib.model.User;
import org.apache.commons.codec.digest.DigestUtils;

public class Core {
    private final BookDAO bookDB = BookDAO.getInstance();
    private final UserDAO userDB = UserDAO.getInstance();
    final GUI gui = GUI.getInstance();
    private final Authenticator authenticator = Authenticator.getInstance();
    private static final Core instance = new Core();
    private boolean registered = false;

    public void start() {
        boolean isRunning = false;
        while (true) {
            while (!isRunning) {
                switch (gui.showLogMenu()) {
                    case "1" -> {
                        User user = this.gui.readLoginAndPasswordFirstTime();
                        userDB.register(user);

                        if (userDB.findByLogin(user.getLogin()).isEmpty())
                            registered = true;
                        gui.showEffectRegistration(registered);
                    }
                    case "2" -> {
                        this.authenticator.authenticate(gui.readLoginAndPassword());
                        isRunning = this.authenticator.getLoggedUser().isPresent();
                        if (!isRunning)
                            System.out.println("Not authorized !");
                    }
                    case "3" -> System.exit(0);
                    default -> System.out.println("Wrong choose !!");
                }
            }
            while (isRunning) {
                switch (gui.showMenu()) {
                    case "1" -> gui.searchBook();
                    case "2" -> {
                        gui.showBooksList();
                        gui.showBorrowEffect(bookDB.rentBook(gui.readTitle(), authenticator.getLoggedUser()));
                    }
                    case "3" -> isRunning = false;
                    case "4" -> {
                        if (authenticator.getLoggedUser().isPresent() && authenticator
                                .getLoggedUser().get().getRole().equals(User.Role.ADMIN)) {
                            gui.showBooksList();
                            bookDB.addBook(gui.readNewBookData());
                        }
                    }
                    case "5" -> {
                        if (authenticator.getLoggedUser().isPresent() && authenticator
                                .getLoggedUser().get().getRole().equals(User.Role.ADMIN)) {
                            gui.showBooksList();
                        }
                    }
                    case "6" -> {
                        if (authenticator.getLoggedUser().isPresent() && authenticator
                                .getLoggedUser().get().getRole().equals(User.Role.ADMIN)) {
                            // GUI.showBorrowedBooksList();
                            gui.showBorrowedBooksList2();
                        }
                    }
                    case "7" -> {
                        if (authenticator.getLoggedUser().isPresent() && authenticator
                                .getLoggedUser().get().getRole().equals(User.Role.ADMIN)) {
                            gui.showBorrowedBooksAfterTheDeadlineList2();

                        }
                    }
                    default -> System.out.println("Wrong choose!");
                    case "8" -> {
                        if (authenticator.getLoggedUser().isPresent() && authenticator
                                .getLoggedUser().get().getRole().equals(User.Role.ADMIN))
                            gui.showUsersList();
                    }
                }
            }
        }
    }

    public static Core getInstance() {
        return instance;
    }
}
