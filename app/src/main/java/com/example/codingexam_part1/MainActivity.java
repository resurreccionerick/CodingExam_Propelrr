package com.example.codingexam_part1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codingexam_part1.databinding.ActivityMainBinding;
import com.example.codingexam_part1.model.ApiResponse;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AppCallback {

    private ActivityMainBinding binding;
    private AppPresenter appPresenter;
    int year, month, day, age;
    String birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NetworkManager networkManager = new NetworkManager();
        ApiService apiService = networkManager.getApiService();
        appPresenter = new AppPresenter(apiService, this);

        onClickListeners();
    }

    private void onClickListeners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGender.setAdapter(adapter);

        binding.btnDob.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> updateAge(year, monthOfYear, dayOfMonth),
                    year, month, day
            );
            datePickerDialog.show();
        });


        binding.btnSubmit.setOnClickListener(v -> {
            submitData();
        });
    }

    private void updateAge(int year, int monthOfYear, int dayOfMonth) {
        Calendar dob = Calendar.getInstance();
        dob.set(year, monthOfYear, dayOfMonth);

        Calendar now = Calendar.getInstance();
        age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (now.get(Calendar.MONTH) < dob.get(Calendar.MONTH) ||
                (now.get(Calendar.MONTH) == dob.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH))) {
            age--; // If birthday hasn't occurred yet this year, subtract 1 from age
        }

        binding.txtDob.setText("Data of birth: " + monthOfYear + "/" + dayOfMonth + "/" + year);
        birthday = monthOfYear + "/" + dayOfMonth + "/" + year;
        binding.txtAgeValue.setText("Age was: " + age);
    }

    private void submitData() {

        String name, email, mobileNum, gender;
        email = binding.etEmail.getText().toString().trim();
        name = binding.etFullName.getText().toString().trim();
        mobileNum = binding.etMobileNumber.getText().toString();
        gender = binding.spinnerGender.getSelectedItem().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a name!", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Please enter a email address!", Toast.LENGTH_SHORT).show();
        } else if (mobileNum.isEmpty()) {
            Toast.makeText(this, "Please enter a mobile number!", Toast.LENGTH_SHORT).show();
        } else if (binding.txtAgeValue.getText().equals("")) {
            Toast.makeText(this, "Please choose a birthday!", Toast.LENGTH_SHORT).show();
        } else if (!name.matches("[a-zA-Z .,]+")) {
            Toast.makeText(this, "Name can only contain letters, spaces, commas, and periods.", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show();
        } else if (!mobileNum.matches("^[0-9]{11}$")) {
            Toast.makeText(this, "Invalid mobile number format!", Toast.LENGTH_SHORT).show();
        } else if (age <= 18) {
            Toast.makeText(this, "Only 18 years old above allowed!, your age was: " + age, Toast.LENGTH_SHORT).show();
        } else {
            FormData formData = new FormData(name, email, mobileNum, birthday, gender, age);
            Log.d("Data details", "NAME " + name + " email:" + email + " mobilenum:" + mobileNum +
                    " gender: " + gender + " bday: " + birthday);
            appPresenter.submitData(formData);

        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResponseDialog(ApiResponse apiResponse) {
        new AlertDialog.Builder(this)
                .setTitle("DATA: ")
                .setMessage("NAME: " + apiResponse.getData().getFullName() + "\n" +
                        "EMAIL: " + apiResponse.getData().getEmail() + "\n" +
                        "MOBILE #: " + apiResponse.getData().getMobileNumber() + "\n" +
                        "AGE: " + apiResponse.getData().getAge() + "\n" +
                        "GENDER: " + apiResponse.getData().getGender() + "\n")
                .setPositiveButton("Done", null)
                .show();
    }
}