package com.example3t.banhangtienloi.model;

import java.util.ArrayList;

public class Donhangmd {
    boolean success;
    String message;
    ArrayList<Donhang> result;

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

    public ArrayList<Donhang> getResult() {
        return result;
    }

    public void setResult(ArrayList<Donhang> result) {
        this.result = result;
    }
}
