package com.example.jf.myapp.UI;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jf.myapp.R;
import com.example.jf.myapp.Utils.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvContent;
    private TextView mChangepwdText;
    private TextView registerText;
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private Button mLoginButton;                      //登录按钮
    private Button mCancleButton;                     //注销按钮
    private CheckBox mRememberCheck;

    private String username, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAccount = findViewById(R.id.login_edit_account);
        mPwd = findViewById(R.id.login_edit_pwd);
        registerText = findViewById(R.id.login_text_register);
        mLoginButton = findViewById(R.id.login_btn_login);
        mCancleButton = findViewById(R.id.login_btn_cancle);
        mChangepwdText = findViewById(R.id.login_text_change_pwd);

        mRememberCheck = findViewById(R.id.Login_Remember);
        registerText.setOnClickListener(this);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        mLoginButton.setOnClickListener(this);
        mCancleButton.setOnClickListener(this);
        mChangepwdText.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_text_register:                            //登录界面的注册按钮
                break;
            case R.id.login_btn_login:                              //登录界面的登录按钮
                login();
                break;
            case R.id.login_btn_cancle:                             //登录界面的注销按钮
                break;
            case R.id.login_text_change_pwd:                          //登录界面的忘记密码按钮
                break;
        }

    }

    private void login() {
        if(isUserNameAndPwdValid()){
            new Thread(() -> {
                try {
                    String username = mAccount.getText().toString();
                    String password = mPwd.getText().toString();
                    URL url = new URL(HttpUtils.URL + HttpUtils.login);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    String body = "username=" + URLEncoder.encode(username, "utf-8") + "&password=" + URLEncoder.encode(password, "utf-8");
                    connection.setDoOutput(true);
                    connection.getOutputStream().write(body.getBytes());
                    if (connection.getResponseCode() == 200) {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = response.toString();
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = -1;
                        msg.obj = "http未连接成功";
                        handler.sendMessage(msg);

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if(msg.obj.equals("1")){
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();//登录成功提示
                }else if(msg.obj.equals("2")){
                    Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("3")){
                    Toast.makeText(MainActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "服务器无连接", Toast.LENGTH_SHORT).show();
            }
        }
    };
    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
