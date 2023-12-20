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

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://e6u5zapw7l.execute-api.ap-southeast-2.amazonaws.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    LoginService service = retrofit.create(LoginService.class);
                    Call<LoginResponse> call = service.login();
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                LoginResponse loginResponse = response.body();
                                // 从loginResponse中获取你需要的信息
                                List<LoginResponse.UserPair> userPairs = loginResponse.getBody().getUserPairs();
                                // 创建两个数组来存储userName和password
                                String[] userNames = new String[userPairs.size()];
                                String[] passwords = new String[userPairs.size()];
                                // 遍历userPairs，将userName和password分别存入数组
                                for (int i = 0; i < userPairs.size(); i++) {
                                    userNames[i] = userPairs.get(i).getUserName();
                                    passwords[i] = userPairs.get(i).getPassword();
                                }
                                // 打印或者使用数组中的数据
                                int i;
                                for (i = 0; i < userPairs.size(); i++) {
                                    if(userName == userNames[i] && password == passwords[i]){
                                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(LoginActivity.this, PersonalView.class);
                                        startActivity(intent);
                                        break;
                                    }
                                }
                                if(i == userPairs.size()){
                                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_LONG).show();
                                }
                                // Toast.makeText(getApplicationContext(), "statusCode: " + statusCode, Toast.LENGTH_LONG).show();
                                // System.out.println("statusCode: " + statusCode);
                                // System.out.println("userNames: " + Arrays.toString(userNames));
                                // System.out.println("passwords: " + Arrays.toString(passwords));
                            } else {
                                // 处理错误的响应
                                System.out.println("response code: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            // 处理请求失败的情况
                            t.printStackTrace();
                        }
                    });
/*
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, PersonalView.class);
                    startActivity(intent);

 */
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