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
import java.util.ArrayList;

import static android.support.v7.widget.AppCompatDrawableManager.get;

/**
 * Created by leeth on 2017-08-22.
 */

public class crawler extends AppCompatActivity {

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
                    ui = new userInformation();
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

    public userInformation crawl(final String userName) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    url = "https://acmicpc.net/user/" + userName;//url = "https://www.acmicpc.net/status/?user_id=" + userName + "&result_id=4";
                    ui = new userInformation();
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
            return ui;
        } catch (java.util.concurrent.ExecutionException e) {
            return ui;
        }
        Toast.makeText(getApplicationContext(),doc.title(),Toast.LENGTH_LONG).show();
        ui = new userInformation();
        ui._id = userName;
        return ui;

        /*
        if(doc.select("#status-table").first() == null) {
            Toast.makeText(getApplicationContext(),"NULL",Toast.LENGTH_LONG).show();
            return ui;
        }
        Toast.makeText(getApplicationContext(),doc.select("#status-table").first().text(),Toast.LENGTH_LONG).show();
        /*
        if(doc.select("#status-table td").first() == null) {
            Toast.makeText(getApplicationContext(),"first == null",Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getApplicationContext(),doc.select("#status-table td").first().toString(),Toast.LENGTH_LONG).show();
        return ui;
        */
    }
}