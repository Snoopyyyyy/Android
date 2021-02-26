package com.androiddev.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Resultat extends AppCompatActivity {

    private int nbBonneReponse;
    private final String[] reponse = {"your brain NullPointerExecption","pas ouff la ...","mouais bof","Pas mal","Bravo !","Félicitation !!"};
    private final int[] image = {R.drawable.image0,R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        Intent intent = getIntent();
        nbBonneReponse = intent.getIntExtra("nbBonneReponse5",0);

        refreshStat();

        ImageView image = findViewById(R.id.res_image);
        Glide.with(getApplicationContext()).load(this.image[nbBonneReponse]).into(image);
        image.setImageResource(this.image[nbBonneReponse]);

        TextView message = findViewById(R.id.res_message);
        message.setText(reponse[nbBonneReponse]);

        TextView note = findViewById(R.id.res_note);
        String n = nbBonneReponse+"/5";
        note.setText(n);
    }

    public void refreshStat(){
        ProgressBar prog = findViewById(R.id.res_progress);
        prog.setMax(5);
        prog.setProgress(5);
        prog.refreshDrawableState();

        RatingBar rate = findViewById(R.id.res_ratingbar);
        rate.setRating(nbBonneReponse);
        rate.refreshDrawableState();
    }
}