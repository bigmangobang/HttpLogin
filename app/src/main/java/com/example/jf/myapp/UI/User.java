package com.example.jf.myapp.UI;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.jf.myapp.Adpater.ZoneAdapter;
import com.example.jf.myapp.Model.Zone;
import com.example.jf.myapp.R;
import com.example.jf.myapp.Thread.ProductHttpThread;

import java.util.List;

public class User extends Activity implements View.OnClickListener {
    private ListView show;
    private Button back_login;
    private TextView updata,userNmae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        show = findViewById(R.id.content_list_view);
        back_login = findViewById(R.id.back);
        updata = findViewById(R.id.up_text);
        userNmae = findViewById(R.id.username);
        String in  = getIntent().getStringExtra("extra_data");
        userNmae.setText(in);
        init_show();
        back_login.setOnClickListener(this);
        updata.setOnClickListener(this);
    }

    private void init_show() {
        //数据，适配器，xml布局
        ProductHttpThread productHttpThread = new ProductHttpThread();
        productHttpThread.start();
        try {
            productHttpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Zone> list =
                JSON.parseArray(productHttpThread.getResult(), Zone.class);
        ZoneAdapter zoneAdapter = new ZoneAdapter(
                User.this, R.layout.zone_item, list);
        show.setAdapter(zoneAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(User.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.up_text:
                Intent intent_up = new Intent(User.this, UpContent.class);
                String in  = getIntent().getStringExtra("extra_data");
                intent_up.putExtra("extra_data", in);
                startActivity(intent_up);
                break;
        }
    }

}
