package edu.ewubd.cse4892rbr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;

public class Login extends AppCompatActivity {
    private EditText userId, password;
    private CheckBox remUser, remLogin;
    private Button exit,login;
    private TextView signup;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userId = findViewById(R.id.userid);
        password = findViewById(R.id.password);
        remUser = findViewById(R.id.remuser);
        remLogin = findViewById(R.id.remlogin);
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(v -> exit());
        login = findViewById(R.id.login);
        login.setOnClickListener(view -> loginUser());
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(view -> signupUser());
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        
        String user = sharedPreferences.getString("userid","null");
        String pass = sharedPreferences.getString("pass","null");

        System.out.println("Shared Pref");
        System.out.println(user);
        System.out.println(pass);
    }
    public void exit(){
        this.onDestroy();
    }
    public void loginUser(){
        String userid = userId.getText().toString();
        String pass = password.getText().toString();
        if(remUser.isChecked()){
            sharedPreferences.edit().putString("userid",userid).apply();
        }
        if(remLogin.isChecked()){
            sharedPreferences.edit().putString("pass",pass).apply();
        }
        System.out.println("Account Created");
        System.out.println(userid);
        System.out.println(pass);

        Intent i = new Intent(Login.this,MainActivity.class);
        startActivity(i);
    }
    public void signupUser(){

    }
}