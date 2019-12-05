package com.github.markmatyushchenko.vt1.entity.user;

public class Administrator extends User {

    private String firstName;
    private String lastName;

    public Administrator(String login, String token, String firstName, String lastName) {
        super(login, token);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return String.format("Administrator(login=%s, firstName=%s, lastName=%s)", getLogin(), firstName, lastName);
    }
}
