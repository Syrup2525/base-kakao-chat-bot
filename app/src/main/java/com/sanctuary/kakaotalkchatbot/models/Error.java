package com.sanctuary.kakaotalkchatbot.models;

import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("status")
    private int status;

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
