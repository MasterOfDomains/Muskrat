package com.jsrwares.muskrat.contacts;

import android.support.annotation.Nullable;

public class Contact {

    private Integer mId = null;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPhone;

    public Contact() {
    }

    public Contact(@Nullable Integer id,
                   String firstName,
                   String lastName,
                   String email,
                   String phone) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mPhone = phone;
    }

    @Override
    public String toString() {
        return mFirstName + " " + mLastName;
    }

    public Integer getId() {
        return mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setFirstName(String name) {
        mFirstName = name;
    }

    public void setLastName(String name) {
        mLastName = name;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}

