package com.androiddev.tp4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.HashMap;

public class Question4 extends AppCompatActivity implements View.OnClickListener{

    private int nbBonneReponse;
    private HashMap<String,Boolean> listValue = new HashMap<>();
    private ArrayList<String> reponse = new ArrayList<>();
    private ArrayList<String> temp;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);

        Intent intent = getIntent();
        nbBonneReponse = intent.getIntExtra("nbBonneReponse3",0);

        System.out.println(nbBonneReponse);
        refreshStat();

        refreshListValue();

        ListView list = findViewById(R.id.q4_list);
        temp = new ArrayList<>(listValue.keySet());
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,temp){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                if(reponse.contains(temp.get(position))){
                    view.setBackgroundColor(Color.rgb(3, 218, 197));
                }else{
                    view.setBackgroundColor(Color.TRANSPARENT);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(reponse.contains(temp.get(position))){
                            reponse.remove(temp.get(position));
                        }else{
                            reponse.add(temp.get(position));
                        }
                        System.out.println(reponse.toString());
                        notifyDataSetChanged();
                    }
                });
                return view;
            }
        };

        list.setAdapter(adapter);

        Button cancel = findViewById(R.id.q4_cancel);
        cancel.setOnClickListener(this);

        Button next = findViewById(R.id.q4_next);
        next.setOnClickListener(this);
    }

    private void refreshListValue() {
        listValue.put("cuisine",false);
        listValue.put("salades",true);
        listValue.put("manoir",false);
        listValue.put("porte",false);
        listValue.put("tringles",true);
        listValue.put("maisons",true);
        listValue.put("hommes",true);
        listValue.put("moustiquaire",false);
        listValue.put("tables",true);
    }

    public void refreshStat(){
        ProgressBar prog = findViewById(R.id.q4_progress);
        prog.setMax(5);
        prog.setProgress(3);
        prog.refreshDrawableState();

        RatingBar rate = findViewById(R.id.q4_ratingbar);
        rate.setRating(nbBonneReponse);
        rate.refreshDrawableState();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.q4_next:
                boolean rep = checkReponse();
                if(!rep) nbBonneReponse--;
                Intent intent = new Intent(this,Question5.class);
                intent.putExtra("nbBonneReponse4",nbBonneReponse);
                System.out.println("last : "+nbBonneReponse);
                startActivity(intent);
                break;

            case R.id.q4_cancel:
                reponse.clear();
                adapter.clear();
                adapter.addAll(listValue.keySet());
                ListView list = findViewById(R.id.q4_list);
                list.setAdapter(adapter);
                break;
            default:
                break;
        }
    }

    private boolean checkReponse() {
        for(String str : listValue.keySet()){
            if((!listValue.get(str) && reponse.contains(str)) || (listValue.get(str) && !reponse.contains(str)))
                return false;
        }
        return true;
    }
}