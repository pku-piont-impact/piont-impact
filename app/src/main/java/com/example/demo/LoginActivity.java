package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button BtnLogin;
    private Button BtnReturnToMain;
    private EditText ETUserName;
    private EditText ETPassword;
    private String userName;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BtnLogin = (Button) findViewById(R.id.login);
        BtnReturnToMain = (Button) findViewById(R.id.ReturnToMain);
        ETUserName = (EditText) findViewById(R.id.userName);
        ETPassword = (EditText) findViewById(R.id.password);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = ETUserName.getText().toString();
                password = ETPassword.getText().toString();
                if(userName.isEmpty()){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                //跳转到地图界面
                else if(!password.equals("123")){
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this,MapActivity.class);
                    startActivity(intent);
                }
            }
        });

        BtnReturnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回到主界面
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}