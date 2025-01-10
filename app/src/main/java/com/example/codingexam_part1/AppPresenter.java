package com.example.codingexam_part1;

import com.example.codingexam_part1.model.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class AppPresenter {
    private ApiService apiService;
    private AppCallback callback;

    public AppPresenter(ApiService apiService, AppCallback callback) {
        this.apiService = apiService;
        this.callback = callback;
    }

    public void submitData(FormData formData) {
        apiService.submitForm(formData).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    callback.showResponseDialog(apiResponse);
                } else {
                    callback.showError("Failed, error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handle failure
                if (callback != null) {
                    callback.showError("Network failure: " + t.getMessage());
                }
            }
        });
    }
}
