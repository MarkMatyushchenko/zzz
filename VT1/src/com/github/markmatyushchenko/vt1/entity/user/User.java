package com.github.markmatyushchenko.vt1.entity.user;

public class User {

    private String login;
    private String token;

    public static User GUEST;

    static {
        GUEST = new User("Guest", "Guest");
    }

    public User(String login, String token) {
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof User)
                && login.equals(((User) obj).login);
    }

    @Override
    public String toString() {
        return String.format("User(login=%s)", login);
    }
}