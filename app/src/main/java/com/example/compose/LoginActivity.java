package com.example.compose;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.app.Activity;

import com.example.compose.dao.UserDao;

public class LoginActivity extends Activity {

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
                else{
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, PersonalView.class);
                    startActivity(intent);
                }
                /*
                new Thread(){
                    @Override
                    public void run() {
                        UserDao userDao = new UserDao();

                        int msg = userDao.login(userName,password);
                        hand1.sendEmptyMessage(msg);
                    }
                }.start();

                 */
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
    @SuppressLint("HandlerLeak")
    final Handler hand1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this,PersonalView.class);
                startActivity(intent);
            } else if (msg.what == 2){
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
            } else if (msg.what == 3){
                Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
            }
        }
    };
}