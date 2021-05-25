package com.example.petrescue.domain.subClasses;


import com.google.gson.Gson;

import retrofit2.Response;

public class ErrorResponse {

    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String formatErrorResponse(Response res) {
        ErrorResponse response = new Gson().fromJson(res.errorBody().charStream(), ErrorResponse.class);
        return response.getStatus() + " - " + response.getMessage();
    }
}