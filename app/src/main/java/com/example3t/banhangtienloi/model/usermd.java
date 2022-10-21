package com.example3t.banhangtienloi.model;

import java.util.ArrayList;

public class usermd {
    boolean success;
    String message;
    ArrayList<user> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<user> getResult() {
        return result;
    }

    public void setResult(ArrayList<user> result) {
        this.result = result;
    }
}
