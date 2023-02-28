package com.wszib.core;

import com.wszib.database.BookDAO;
import com.wszib.database.UserDAO;
import com.wszib.gui.GUI;
import com.wszib.model.User;

public class Core {
    final BookDAO bookDB = BookDAO.getInstance();
    final UserDAO userDB = UserDAO.getInstance();
    final GUI gui = GUI.getInstance();
    final Authenticator authenticator = Authenticator.getInstance();
    static final Core instance = new Core();
    public void start(){


        boolean isRunning = false;

        while(true) {
            while (!isRunning) {
                switch (GUI.showLogMenu()) {
                    case "1":
                        userDB.register(GUI.readLoginAndPasswordFirstTime());
                        break;
                    case "2":
                        authenticator.authenticate(gui.readLoginAndPassword());
                        isRunning = authenticator.loggedUser != null;
                        if (!isRunning)
                            System.out.println("Not authorized !");
                        break;
                    case "3":
                        System.exit(0);
                    default:
                        System.out.println("Wrong choose !!");
                        break;
                }
            }

            while (isRunning) {
                switch (GUI.showMenu()) {
                    case "1":
                        this.gui.searchBook();
                        break;
                        case "2":
                            GUI.showBooksList();
                            GUI.showBorrowEffect(bookDB.rentBook(GUI.readTitle(),this.authenticator.loggedUser));
                            break;
                    case "3":
                        isRunning = false;
                        break;
                    case "4":
                        if (authenticator.loggedUser != null && authenticator
                                .loggedUser.getRole().equals(User.Role.ADMIN)) {
                            GUI.showBooksList();
                            bookDB.addBook(GUI.readNewBookData());
                        }
                        break;
                    case "5":
                        if (authenticator.loggedUser != null && authenticator
                                .loggedUser.getRole().equals(User.Role.ADMIN)) {
                            GUI.showBooksList();
                        }
                        break;
                    case "6":
                        if (authenticator.loggedUser != null && authenticator
                                .loggedUser.getRole().equals(User.Role.ADMIN)) {
                           // GUI.showBorrowedBooksList();
                            GUI.showBorrowedBooksList2();
                        }
                        break;
                    case "7":
                        if (authenticator.loggedUser != null && authenticator
                                .loggedUser.getRole().equals(User.Role.ADMIN)) {
                            GUI.showBorrowedBooksAfterTheDeadlineList2();

                        }
                        break;
                    default:
                        System.out.println("Wrong choose!");
                        break;
                    case "8":
                        if (authenticator.loggedUser != null && authenticator
                                .loggedUser.getRole().equals(User.Role.ADMIN))
                            GUI.showUsersList();
                        break;

                }
            }
        }
    }
    public static Core getInstance() {
        return instance;
    }
}
