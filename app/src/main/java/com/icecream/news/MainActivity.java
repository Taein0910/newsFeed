package com.icecream.news;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ArrayList<String> array_article;
    private ListView mListView;

    private String htmlPageUrl = "http://www.yonhapnews.co.kr/"; //파싱할 홈페이지의 URL주소
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat = "";
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);
        array_article = new ArrayList<>();



        this.mContext = getApplicationContext();
        mMountainAdapter = new MountainAdapter(mContext, array_mountain);

        mListView.setAdapter(mMountainAdapter)

        textviewHtmlDocument = (TextView)findViewById(R.id.textView);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod());

        Log.e("debug", "onCreate");
        System.out.println((cnt + 1) + "번째 파싱");
        textviewHtmlDocument = (TextView) findViewById(R.id.textView);
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        cnt++;




        Button htmlTitleButton = (Button) findViewById(R.id.button);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("debug", "클릭됨");
                System.out.println((cnt + 1) + "번째 파싱");
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;
            }
        });
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("debug", "onPreExecute");

        }


        @Override

        protected Void doInBackground(Void... params) {

            Log.e("debug", "doInBackground");
            try {
                Log.e("debug", "documentTrytoget");
                Document doc = Jsoup.connect("https://www.yna.co.kr/").get();
                Log.e("debug", "documentGetFinish");


                //테스트1
                Elements titles = doc.select("div.news-con h1.tit-news");

                System.out.println("-------------------------------------------------------------");


                for (Element e : titles) {
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n\n";

                }

                //테스트2


                titles = doc.select("div.news-con h2.tit-news");

                System.out.println("-------------------------------------------------------------");
                for (Element e : titles) {
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n\n";

                }

                //테스트3
                titles = doc.select("li.section02 div.con h2.news-tl");

                System.out.println("-------------------------------------------------------------");
                for (Element e : titles) {
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n\n";

                }
                System.out.println("-------------------------------------------------------------");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //textviewHtmlDocument.setText(htmlContentInStringFormat);
            String[] target = htmlContentInStringFormat.split("\\n");

            for(int i=0;i<target.length;i++) {
                arrayList.add(target[i]);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
}