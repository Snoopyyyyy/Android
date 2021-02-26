package com.androiddev.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

public class Question5 extends AppCompatActivity implements View.OnClickListener{

    private int nbBonneReponse;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        Intent intent = getIntent();
        nbBonneReponse = intent.getIntExtra("nbBonneReponse4",0);

        System.out.println(nbBonneReponse);
        refreshStat();

        Button cancel = findViewById(R.id.q5_cancel);
        cancel.setOnClickListener(this);
        Button next = findViewById(R.id.q5_next);
        next.setOnClickListener(this);

        input = findViewById(R.id.q5_r);
    }

    public void refreshStat(){
        ProgressBar prog = findViewById(R.id.q5_progress);
        prog.setMax(5);
        prog.setProgress(4);
        prog.refreshDrawableState();

        RatingBar rate = findViewById(R.id.q5_ratingbar);
        rate.setRating(nbBonneReponse);
        rate.refreshDrawableState();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.q5_cancel:
                input.setText("");
                break;

            case R.id.q5_next:
                if(input.getText().toString() == "") {
                    Toast.makeText(this,"Réponse invalide...",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!input.getText().toString().toUpperCase().equalsIgnoreCase("Elles sont parties au toilette.".toUpperCase()))
                    nbBonneReponse--;
                Intent intent = new Intent(this,Resultat.class);
                intent.putExtra("nbBonneReponse5",nbBonneReponse);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}