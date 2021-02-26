package com.androiddev.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Acceuil extends AppCompatActivity implements View.OnClickListener{

    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        email = findViewById(R.id.login_input);
        password = findViewById(R.id.password_input);

        Button b = findViewById(R.id.valid);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Pattern pattern = Pattern.compile("^([\\w-\\.]+)@((?:[\\w]+\\.)+)([a-zA-Z]{2,4})");
        Matcher matcher = pattern.matcher(email.getText().toString());
        if (!matcher.matches() || password.getText().toString().length() < 8) {
            Toast.makeText(getApplicationContext(), "Connection impossible ...", Toast.LENGTH_LONG).show();
        }else {
            Intent in = new Intent(this, Question1.class);
            startActivity(in);
        }
    }
}