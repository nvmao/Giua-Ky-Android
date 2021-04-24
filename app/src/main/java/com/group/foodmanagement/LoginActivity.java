package com.group.foodmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btnLogin;
    private TextView wrongInput;
    private int count=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        wrongInput=(TextView)findViewById(R.id.wrongInput);
        wrongInput.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(username.getText().toString(),password.getText().toString());
            }
        });
    }

    private void validate(String username, String password){


        if((username.equals("betran"))&&(password.equals("betran"))){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }else {
            count--;
            wrongInput.setVisibility(View.VISIBLE);
            if(count==0){
                btnLogin.setEnabled(false);
            }
        }
    }
}