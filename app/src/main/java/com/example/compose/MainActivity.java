package com.example.compose;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.app.Activity;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView title;
    private Button btnGoToLogin;
    private Button btnGoToSignIn;

    private static final String TAG = "mysql-party-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGoToLogin = (Button) findViewById(R.id.JumpToLogin);
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到登录界面
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void reg(View view){
        startActivity(new Intent(getApplicationContext(),SignInActivity.class));
    }
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        btnGoToSignIn = (Button) findViewById(R.id.JumpToSignIn);
        btnGoToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到注册界面
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }
     */
}

