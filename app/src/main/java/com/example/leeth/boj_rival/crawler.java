package com.example.leeth.boj_rival;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static android.support.v7.widget.AppCompatDrawableManager.get;
import static java.lang.System.exit;

/**
 * Created by leeth on 2017-08-22.
 */

public class crawler extends AppCompatActivity {

    public String url;
    public userInformation ui;
    Document doc_userinfo, doc_status;
    String result;
    String userName;
    String status;

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ui = new userInformation();

            if (status.equals("isExist")) {
                url = "https://acmicpc.net/user/" + userName;
                doc_userinfo = null;
            } else if (status.equals("crawl")) {
                url = "https://www.acmicpc.net/status/?user_id=" + userName + "&result_id=4";
                doc_status = null;
            } else {
                //TODO
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (status.equals("isExist")) {
                    doc_userinfo = Jsoup.connect(url).get();
                } else if (status.equals("crawl")) {
                    doc_status = Jsoup.connect(url).get();
                } else {
                    //TODO
                }
            } catch (MalformedURLException | ProtocolException exception) {
                exception.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    public boolean isExist(final String userName) {
        status = "isExist";
        this.userName = userName;

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        try {
            jsoupAsyncTask.execute().get();
        } catch (java.lang.InterruptedException e) {

        } catch (java.util.concurrent.ExecutionException e) {

        }

        if(doc_userinfo != null && doc_userinfo.select("div.error-v1").first() == null) return true;
        else return false;
    }

    public userInformation crawl(final String userName) {
        status = "crawl";
        this.userName = userName;

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        try {
            jsoupAsyncTask.execute().get();
        } catch (java.lang.InterruptedException e) {

        } catch (java.util.concurrent.ExecutionException e) {

        }

        ui = new userInformation();
        ui._id = userName;
        Elements problemPool = doc_userinfo.select("div.panel-body").first().select("span.problem_number");
        for (Element e : problemPool) {
            ui.problems.put(e.text(),"");
        }
        ui.last = Integer.parseInt(doc_status.select("#status-table tbody tr td").first().ownText());

        return ui;
    }
}