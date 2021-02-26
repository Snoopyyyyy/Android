package com.androiddev.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class Question1 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{

    private int nbBonneReponse = 5;
    private int reponse = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        RadioGroup group = findViewById(R.id.q1_groupe);
        group.setOnCheckedChangeListener(this);

        Button cancel = findViewById(R.id.q1_cancel);
        cancel.setOnClickListener(this);

        Button next = findViewById(R.id.q1_next);
        next.setOnClickListener(this);

        ProgressBar prog = findViewById(R.id.q1_progress);
        prog.setMax(5);
        prog.setProgress(0);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radio = findViewById(checkedId);
        reponse = 2;
        if(radio == null)
            return;
        if(radio.getText().toString().equalsIgnoreCase("12 août 1954"))
            reponse = 1;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.q1_cancel:
                RadioGroup group = findViewById(R.id.q1_groupe);
                group.clearCheck();
                reponse = 0;
                break;

            case R.id.q1_next:
                if(reponse == 0) {
                    Toast.makeText(this, "Réponse invalide...", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent in = new Intent(this,Question2.class);
                if(reponse == 2) nbBonneReponse--;
                in.putExtra("nbBonneReponse",nbBonneReponse);
                startActivity(in);
                break;

            default:
                break;
        }
    }
}