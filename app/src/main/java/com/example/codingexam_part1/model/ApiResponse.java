package com.example.codingexam_part1.model;

import com.example.codingexam_part1.FormData;

public class ApiResponse {

    private String message;
    private FormData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FormData getData() {
        return data;
    }

    public void setData(FormData data) {
        this.data = data;
    }
}
