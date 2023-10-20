package com.wszib.model;

public class User {

    private int id;
    private String login;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;

    public User(String login, String password, Role role,String firstName, String lastName){
        this.login = login;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(){
        this.role=Role.USER;
    }

//    public int getId() {
//        return id;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public enum Role{
        ADMIN,
        USER
    }
    @Override
    public String toString() {
        return String.format("%-25s%-20s%-15s%-35s%-5s",firstName,lastName,login,password,role);
    }

}