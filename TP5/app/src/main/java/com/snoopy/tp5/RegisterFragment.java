package com.snoopy.tp5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private EditText email;
    private EditText password;
    private EditText passwordConfirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_authentication_register, container, false);
        this.email = v.findViewById(R.id.authentication_register_email);
        this.password = v.findViewById(R.id.authentication_register_password);
        this.passwordConfirm = v.findViewById(R.id.authentication_register_password2);

        Button cancel = v.findViewById(R.id.authentication_register_button_cancel);
        cancel.setOnClickListener(this);
        Button register = v.findViewById(R.id.authentication_register_button_register);
        register.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        AuthenticationActivity main = AuthenticationActivity.getInstance();
        if(main == null) return;
        if(v.getId() == R.id.authentication_register_button_register){
            main.addLogin(email.getText().toString(),password.getText().toString(),passwordConfirm.getText().toString());
        }else{
            main.switchFragment(0);
        }
    }
}
