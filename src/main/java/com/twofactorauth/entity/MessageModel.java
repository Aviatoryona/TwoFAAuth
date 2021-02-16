package com.twofactorauth.entity;

/**
 * A class to return response
 */
public class MessageModel {
    boolean success;
    boolean message;
    boolean data;

    public MessageModel(boolean success, boolean message, boolean data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public MessageModel(boolean success, boolean message) {
        this.success = success;
        this.message = message;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
