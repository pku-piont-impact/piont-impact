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

import com.example.compose.entity.User;
public class SignInActivity extends Activity {
    private static final String TAG = "mysql-test-register";
    private Button BtnSignIn;
    private Button BtnReturnToMain;
    private EditText ETUserName;
    private EditText ETPassword;
    private EditText ETPasswordAgain;
    private String userName;
    private String password;
    private String passwordAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        BtnSignIn = (Button) findViewById(R.id.SignIn);
        BtnReturnToMain = (Button) findViewById(R.id.ReturnToMain);
        ETUserName = (EditText) findViewById(R.id.userName);
        ETPassword = (EditText) findViewById(R.id.password);
        ETPasswordAgain = (EditText) findViewById(R.id.passwordAgain);

        BtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = ETUserName.getText().toString();
                password = ETPassword.getText().toString();
                passwordAgain = ETPasswordAgain.getText().toString();
                if(userName.isEmpty()){
                    Toast.makeText(SignInActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()){
                    Toast.makeText(SignInActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                else if(passwordAgain.isEmpty()){
                    Toast.makeText(SignInActivity.this,"请确认密码",Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(passwordAgain)){
                    Toast.makeText(SignInActivity.this,"两次输入的密码不一样",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignInActivity.this,"注册成功！",Toast.LENGTH_LONG).show();
                    //跳转回主界面
                    Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        BtnReturnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转回主界面
                Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    final Handler hand = new Handler()
    {
        public void handleMessage(Message msg) {
            if(msg.what == 0) {
                Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();
            }
            else if(msg.what == 1) {
                Toast.makeText(getApplicationContext(),"该账号已经存在，请换一个账号",Toast.LENGTH_LONG).show();
            }
            else if(msg.what == 2) {
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                //将想要传递的数据用putExtra封装在intent中
                intent.putExtra("a","注册");
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        }
    };
}