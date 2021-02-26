package com.androiddev.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

public class Question3 extends AppCompatActivity implements View.OnClickListener{

    private int nbBonneReponse;
    private int reponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        Intent intent = getIntent();
        nbBonneReponse = intent.getIntExtra("nbBonneReponse2",0);

        System.out.println(nbBonneReponse+" -> get "+intent.getIntExtra("nbBonneReponse2",0));
        refreshStat();

        Button cancel = findViewById(R.id.q3_cancel);
        cancel.setOnClickListener(this);

        Button next = findViewById(R.id.q3_next);
        next.setOnClickListener(this);

        Button vrai = findViewById(R.id.q3_true);
        vrai.setOnClickListener(this);

        Button faux = findViewById(R.id.q3_false);
        faux.setOnClickListener(this);
    }

    public void refreshStat(){
        ProgressBar prog = findViewById(R.id.q3_progress);
        prog.setMax(5);
        prog.setProgress(2);
        prog.refreshDrawableState();

        RatingBar rate = findViewById(R.id.q3_ratingbar);
        rate.setRating(nbBonneReponse);
        rate.refreshDrawableState();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.q3_cancel:
                reponse = 0;
                refreshVisibility();
                break;

            case R.id.q3_next:
                if(reponse == 0) {
                    Toast.makeText(this, "Réponse invalide...", Toast.LENGTH_LONG).show();
                    return;
                }
                if(reponse == 2) nbBonneReponse--;
                Intent intent = new Intent(this,Question4.class);
                intent.putExtra("nbBonneReponse3",nbBonneReponse);
                startActivity(intent);
                break;

            case R.id.q3_true:
                reponse = 1;
                refreshVisibility();
                break;

            case R.id.q3_false:
                reponse = 2;
                refreshVisibility();
                break;

            default:
                break;
        }
    }

    public void refreshVisibility(){
        Button vrai = findViewById(R.id.q3_true);
        Button faux = findViewById(R.id.q3_false);

        if(reponse == 1) {
            vrai.setBackgroundColor(Color.rgb(124, 255, 49));
            faux.setBackgroundColor(Color.rgb(3,218,197));
        }else if(reponse == 2){
            faux.setBackgroundColor(Color.rgb(124, 255, 49));
            vrai.setBackgroundColor(Color.rgb(3,218,197));
        }else{
            vrai.setBackgroundColor(Color.rgb(3,218,197));
            faux.setBackgroundColor(Color.rgb(3,218,197));
        }
    }
}