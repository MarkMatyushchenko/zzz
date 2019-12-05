package com.github.markmatyushchenko.vt1.entity.user;

public class Client extends User {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Client(String login, String token, String email, String firstName, String lastName) {
        this(login, token, email, firstName, lastName, "");
    }

    public Client(String login, String token, String email, String firstName, String lastName, String phoneNumber) {
        super(login, token);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("Client(login=%s, email=%s, firstName=%s, lastName=%s, phoneNumber=%s)", getLogin(), email, firstName, lastName, phoneNumber);
    }
}
