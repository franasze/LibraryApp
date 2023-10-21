package com.wszib.core;

import com.wszib.database.BookDAO;
import com.wszib.database.UserDAO;
import com.wszib.exceptions.IncorrectPasswordException;
import com.wszib.exceptions.UserNotFoundException;
import com.wszib.gui.GUI;
import com.wszib.model.User;

public class Core {
    private static final BookDAO bookDB = BookDAO.getInstance();
    private static final UserDAO userDB = UserDAO.getInstance();
    private static final GUI gui = GUI.getInstance();
    private static final Authenticator authenticator = Authenticator.getInstance();
    private static final Core instance = new Core();

    public void start() {
        boolean registered;
        boolean isRunning = false;

        while (true) {
            while (!isRunning) {
                switch (gui.showLogMenu()) {
                    case "1" -> {
                        registered = false;
                        User user = gui.readLoginAndPasswordFirstTime();
                        if (userDB.findByLogin(user.getLogin()).isEmpty()) {
                            userDB.register(user);
                            registered = true;
                        }
                        gui.showEffectRegistration(registered);
                    }
                    case "2" -> {
                        try {
                            authenticator.authenticate(gui.readLoginAndPassword());
                            isRunning = authenticator.getLoggedUser().isPresent();
                        } catch (UserNotFoundException | IncorrectPasswordException e) {
                            System.out.println("Incorrect authentication");
                        }
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
                        System.out.println("\n");
                    }
                }
            }
        }
    }

    public static Core getInstance() {
        return instance;
    }
}
