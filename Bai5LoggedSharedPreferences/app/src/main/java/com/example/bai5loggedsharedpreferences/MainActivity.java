package com.example.bai5loggedsharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private Button buttonSave, buttonShow, buttonLogout;
    private TextView textViewResult;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_LOGGED_IN = "logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextUsername = findViewById(R.id.editTextUsername);
        buttonSave = findViewById(R.id.buttonSave);
        buttonShow = findViewById(R.id.buttonShow);
        buttonLogout = findViewById(R.id.buttonLogout);
        textViewResult = findViewById(R.id.textViewResult);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        buttonSave.setOnClickListener(v -> saveUsername());
        buttonShow.setOnClickListener(v -> showUsername());
        buttonLogout.setOnClickListener(v -> logout());
        checkLoggedInStatus();
    }

    private void saveUsername() {
        String username = editTextUsername.getText().toString().trim();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.apply();
        textViewResult.setText("Username saved");
    }

    private void showUsername() {
        String username = sharedPreferences.getString(KEY_USERNAME, "No name defined");
        boolean loggedIn = sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
        if (loggedIn) {
            textViewResult.setText("Username: " + username);
        } else {
            textViewResult.setText("User not logged in");
        }
    }

    private void  logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LOGGED_IN, false);
        editor.apply();
        textViewResult.setText("Logged out");
    }

    private void checkLoggedInStatus() {
        boolean loggedIn = sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
        if (loggedIn) {
            showUsername();
        } else {
            textViewResult.setText("User not logged in");
        }
    }
}