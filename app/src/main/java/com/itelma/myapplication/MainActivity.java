package com.itelma.myapplication;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.lang.*;
import java.net.URL;
import java.util.*;
import java.net.URLConnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public TextView text;
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new Update().execute();
                }
                catch (Exception e) {
                    text.setText("Exception:" + e.getMessage());
                }
            }
        });
    }

    class Update extends AsyncTask<Void, Void, Void> {

        StringBuilder resultString = new StringBuilder();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            text.setText("Processing...please wait");
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL url = new URL("http://www.btlt.ru/info/temperature");
                URLConnection conn = url.openConnection();
                conn.connect();
                Scanner scan = new Scanner(conn.getInputStream());
                while (scan.hasNext()) {
                    resultString.append(scan.next());
                }
                scan.close();
            }
            catch (Exception e) {
                resultString.setLength(0);
                resultString.append("Exception:" + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            text.setText(resultString);
        }
    }
}
