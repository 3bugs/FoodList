package com.example.foodlist.model;

/**
 * Created by Promlert on 10/24/2016.
 */

public class ResponseStatus {

    public final boolean success;
    public final String message;

    public ResponseStatus(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
