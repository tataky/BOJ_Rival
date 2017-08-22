package com.example.leeth.boj_rival;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.support.v7.widget.AppCompatDrawableManager.get;

/**
 * Created by leeth on 2017-08-22.
 */

public class crawler {

    public String url;
    public userInformation ui;
    Document doc;
    String result;

    public boolean isExist(final String userName) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    url = "https://acmicpc.net/user/" + userName;
                    doc = null;
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        doc = Jsoup.connect(url).get();
                    } catch (MalformedURLException | ProtocolException exception) {
                        exception.printStackTrace();
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    System.out.println(result);
                }
            }.execute().get();
        } catch (java.lang.InterruptedException e) {
            return false;
        } catch (java.util.concurrent.ExecutionException e) {
            return false;
        }
        if(doc != null && doc.select("div.error-v1").first() == null) return true;
        else return false;
    }
    public userInformation crawl(String userName) {
        return new userInformation();
    }
}