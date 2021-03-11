package com.snoopy.tp5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AuthenticationActivity extends AppCompatActivity {

    private static AuthenticationActivity main;
    private JSONArray jsonArrayLoggin;
    private JSONObject last;
    private boolean stayConnect;
    private File saveFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        main = this;
        this.stayConnect = false;
        try {
            setUp();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUp() throws IOException, JSONException {
        this.saveFile = new File(getApplicationContext().getCacheDir().getAbsolutePath()+File.separator+"Loggin.json");
        if(this.saveFile.exists()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(this.saveFile));
                StringBuilder stringBuilder = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null)
                    stringBuilder.append(str);
                JSONObject log = new JSONObject(stringBuilder.toString());
                this.jsonArrayLoggin = log.getJSONArray("Login");
                this.stayConnect = log.getBoolean("Stay");
                this.last = log.getJSONObject("Last");
            }catch (Exception e){
                this.jsonArrayLoggin = new JSONArray();
                this.last = new JSONObject();
                this.last.put("Email","Admin@gmail.com");
                this.last.put("Password","Admin");
                this.jsonArrayLoggin.put(this.last);
                saveInfo();
            }
        }else{
            boolean create = this.saveFile.createNewFile();
            if(create){
                this.jsonArrayLoggin = new JSONArray();
                this.last = new JSONObject();
                this.last.put("Email","Admin@gmail.com");
                this.last.put("Password","Admin");
                this.jsonArrayLoggin.put(this.last);
                saveInfo();
            }
        }
    }

    public void saveInfo() throws JSONException, IOException {
        if(this.saveFile.exists()){
            JSONObject log = new JSONObject();
            log.put("Login",this.jsonArrayLoggin);
            log.put("Stay",this.stayConnect);
            log.put("Last",this.last);
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.saveFile));
            writer.write(log.toString());
            writer.flush();
            writer.close();
        }
    }

    public void addLogin(String email,String password){
        try {
            if(getIdFromName(email) == -1) {
                JSONObject obj = new JSONObject();
                obj.put("Email",email);
                obj.put("Password",password);
                this.jsonArrayLoggin.put(obj);
                saveInfo();
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),getText(R.string.failed_addLogin),Toast.LENGTH_LONG).show();
        }
    }

    public void tryConnect(String email,String password,boolean isStay){
        try {
            int id = getIdFromName(email);
            if(id == -1)
                throw new Exception();
            JSONObject log = this.jsonArrayLoggin.getJSONObject(id);
            if(log.getString("Password").equalsIgnoreCase(password)){
                this.last = log;
                this.stayConnect = isStay;
                saveInfo();
                System.out.println("WORK");
                /*Intent intent = new Intent();
                startActivity(intent);*/
                System.exit(0);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),getText(R.string.failed_connect),Toast.LENGTH_LONG).show();
        }
    }

    private int getIdFromName(String email) throws JSONException {
        for(int i=0;i<this.jsonArrayLoggin.length();i++){
            JSONObject obj = this.jsonArrayLoggin.getJSONObject(i);
            if(obj.getString("Email").equalsIgnoreCase(email)){
                return i;
            }
        }
        return -1;
    }

    public static AuthenticationActivity getInstance(){
        return main;
    }

    public boolean stayLog(){
        return this.stayConnect;
    }

    public JSONObject getLast(){
        return this.last;
    }
}
