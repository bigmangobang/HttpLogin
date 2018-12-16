package com.example.jf.myapp.Thread;

import com.example.jf.myapp.Utils.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductHttpThread extends Thread{
    private String result;
    public void run() {
        //http请求
        try {
            URL url = new URL(HttpUtils.URL + HttpUtils.showContent);
            HttpURLConnection httpURLConnection  = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(String.valueOf("POST"));
            httpURLConnection.setConnectTimeout(5*1000);
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String temp ;
            while((temp = bufferedReader.readLine()) != null){
                stringBuffer.append(temp);
            }
            setResult(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
