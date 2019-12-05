package com.github.markmatyushchenko.vt1.entity;

public class PhoneContact {

    private String phoneNumber;
    private String holder;

    public PhoneContact(String phoneNumber, String holder) {
        this.phoneNumber = phoneNumber;
        this.holder = holder;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHolder() {
        return holder;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PhoneContact)
                && phoneNumber.equals(((PhoneContact) obj).phoneNumber)
                && holder.equals(((PhoneContact) obj).holder);
    }

    @Override
    public String toString() {
        return String.format("PhoneContact(phoneNumber=%s, holder=%s)", phoneNumber, holder);
    }
}
