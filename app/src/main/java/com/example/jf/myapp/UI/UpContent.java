package com.example.jf.myapp.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class UpContent extends Activity implements View.OnClickListener {
    private TextView back_User, addContent;
    private EditText word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_content);
        back_User = findViewById(R.id.back);
        addContent = findViewById(R.id.insert_word);
        word = findViewById(R.id.content_editText);
        addContent.setOnClickListener(this);
        back_User.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent_back = new Intent(UpContent.this, User.class);
                startActivity(intent_back);
            case R.id.insert_word:
                add_click();

        }

    }

    private void add_click() {
        if (!word.getText().toString().trim().equals("")) {
            new Thread(() -> {
                try {
                    String content = word.getText().toString();
                    String username  = getIntent().getStringExtra("extra_data");
                    URL url = new URL(HttpUtils.URL + HttpUtils.addContent);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    String body = "username=" + URLEncoder.encode(username, "utf-8") + "&content=" + URLEncoder.encode(content, "utf-8");
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
                if(msg.obj.equals("1"))
                    Toast.makeText(UpContent.this, "发表成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpContent.this, User.class);    //切换User Activity至Login Activity
                    String username  = getIntent().getStringExtra("extra_data");
                    intent.putExtra("extra_data", username);
                    startActivity(intent);
                    finish();
            } else {
                Toast.makeText(UpContent.this, "服务器无连接", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
