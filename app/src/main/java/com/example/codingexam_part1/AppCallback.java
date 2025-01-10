package com.example.codingexam_part1;

import com.example.codingexam_part1.model.ApiResponse;

public interface AppCallback {
    void showError(String message);
    void showResponseDialog(ApiResponse apiResponse);
}
