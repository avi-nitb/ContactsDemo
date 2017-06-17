package com.contactsdemo;

import java.io.Serializable;

/**
 * Created by SONI on 5/13/2017.
 */

public class Contacts implements Serializable {
    private final static long serialVersionUID = 25194164L;

    private String name;
    private String phoneNumber;
    private String messageText;
    private long dateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
