package com.itelma.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.*;
import java.io.*;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public TextView status;
    public Button button;
    public TextView temp;
    public TextView pres;
    public TextView hum;
    public TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Request().execute();
            }
        });
        temp = (TextView) findViewById(R.id.textView2);
        pres = (TextView) findViewById(R.id.textView3);
        hum = (TextView) findViewById(R.id.textView4);
        info = (TextView) findViewById(R.id.textView6);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        new Request().execute();
    }

    class Request extends AsyncTask<Void, Void, Void> {

        String[] params;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            status.setText("requesting...");
        }

        @Override
        protected Void doInBackground(Void... stub) {

            try {
//                URL url = new URL("http://www.btlt.ru/info/temperature");
//                URLConnection conn = url.openConnection();
//                conn.connect();

                Document doc = Jsoup.connect("http://www.btlt.ru/info/temperature").get();
                String strong = doc.body().getElementsByTag("strong").text();
                params = strong.split(" ");
                result = "ok";
            }
            catch (IOException e) {
                //result = "IOException:" + e.getMessage();
                result = "no internet connection!";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void stub) {
            super.onPostExecute(stub);
            status.setText(result);
            if( result.contains("ok") ) {
                temp.setText(params[0]);
                pres.setText(params[1] + "мм.рт.ст");
                hum.setText(params[2] + "%");
            }
        }
    }
}
