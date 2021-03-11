package com.snoopy.tp5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class LoginFragment extends Fragment {

    private TextView email;
    private EditText password;
    private CheckBox stay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_authentication_login, container, false);

        email = v.findViewById(R.id.authentication_login_email);
        password = v.findViewById(R.id.authentication_login_password);
        stay = v.findViewById(R.id.authentication_login_stay_log);

        Button valider = v.findViewById(R.id.authentication_login_button_login);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity main = AuthenticationActivity.getInstance();
                if(main != null){
                    main.tryConnect(email.getText().toString(),password.getText().toString(),stay.isChecked());
                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Thread(){
            @Override
            public void run() {
                boolean check = false;
                do {
                    AuthenticationActivity main = AuthenticationActivity.getInstance();
                    if (main != null) {
                        System.out.println(main.getLast()+" "+main.stayLog());
                        if (main.stayLog()) {
                            check = true;
                            try {
                                JSONObject last = main.getLast();
                                email.setText(last.getString("Email"));
                                password.setText(last.getString("Password"));
                                stay.setChecked(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }while(!check);
            }
        }.start();
    }
}
