package com.androiddev.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

public class Question2 extends AppCompatActivity implements View.OnClickListener {

    private int nbBonneReponse;
    private CheckBox[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        Intent intent = getIntent();
        nbBonneReponse = intent.getIntExtra("nbBonneReponse",0);

        System.out.println(nbBonneReponse);
        refreshStat();

        Button cancel = findViewById(R.id.q2_cancel);
        cancel.setOnClickListener(this);

        Button next = findViewById(R.id.q2_next);
        next.setOnClickListener(this);

        list = new CheckBox[]{findViewById(R.id.q2_r1), findViewById(R.id.q2_r2),findViewById(R.id.q2_r3),findViewById(R.id.q2_r4),findViewById(R.id.q2_r5),findViewById(R.id.q2_r6)};
    }

    public void refreshStat(){
        ProgressBar prog = findViewById(R.id.q2_progress);
        prog.setMax(5);
        prog.setProgress(1);
        prog.refreshDrawableState();

        RatingBar rate = findViewById(R.id.q2_ratingbar);
        rate.setRating(nbBonneReponse);
        rate.refreshDrawableState();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.q2_cancel:
                for (CheckBox b:list)
                    b.setChecked(false);
                break;

            case R.id.q2_next:
                nbBonneReponse--;
                if(!list[0].isChecked() && list[1].isChecked() && list[2].isChecked()  &&  list[3].isChecked() && !list[4].isChecked() && !list[5].isChecked())
                    nbBonneReponse++;
                Intent intent = new Intent(this,Question3.class);
                System.out.println("last : "+nbBonneReponse);
                intent.putExtra("nbBonneReponse2",nbBonneReponse);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}