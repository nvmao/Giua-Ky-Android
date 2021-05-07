package com.group.foodmanagement.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.group.foodmanagement.R;
import com.group.foodmanagement.model.User;
import com.group.foodmanagement.repository.UserRepository;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        usernameEditText.setText("mao");
        passwordEditText.setText("1");

        loginButton.setEnabled(true);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                User user = UserRepository.login(usernameEditText.getText().toString(),passwordEditText.getText().toString());

                if(user != null){
                    updateUiWithUser();

                    setResult(Activity.RESULT_OK);
                    finish();
                }
                else{
                    loadingProgressBar.setVisibility(View.INVISIBLE);

                    final float currentX = loginButton.getX();

                    loginButton.animate().x(loginButton.getX() + 200).setDuration(200).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            loginButton.animate().x(loginButton.getX() - 400).setDuration(200).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    loginButton.animate().x(currentX).setDuration(200);
                                }
                            });
                        }
                    });

                }
            }
        });
    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.welcome) + "mao";
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
