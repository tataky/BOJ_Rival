package com.example.leeth.boj_rival;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by leeth on 2017-08-22.
 */

public class crawler extends AppCompatActivity {

    public String url;
    public userInformation ui;
    Document doc_userinfo, doc_status, doc_additional;
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
            } else if (status.equals("additionalCrawl")){
                url = "https://www.acmicpc.net/status/?user_id=" + userName + "&result_idx=4&top=" + last;
                doc_additional = null;
            } else {
                Log.d("debug", "what I crawl?");
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (status.equals("isExist")) {
                    doc_userinfo = Jsoup.connect(url).maxBodySize(0).get();
                } else if (status.equals("crawl")) {
                    doc_status = Jsoup.connect(url).maxBodySize(0).get();
                } else if (status.equals("additionalCrawl")){
                    doc_additional = Jsoup.connect(url).maxBodySize(0).get();
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
        Elements problemName = doc_userinfo.select("div.panel-body").first().select("span.problem_title");
        Iterator<Element> e1 = problemName.iterator();
        for (Element e : problemPool) {
            Element e2 = e1.next();
            ui.problems.put(e.text() + "/" + e2.text(), "");
        }
        ui.last = Integer.parseInt(doc_status.select("#status-table tbody tr td").first().ownText());
        ui.updated = ui.last;
        return ui;
    }

    HashMap<String, String> updatedList;
    int last;

    void recursiveCrawl(final int last) {
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        this.last = last;

        try {
            jsoupAsyncTask.execute().get();

            Elements list = doc_additional.select("table#status-table > tbody").first().select("td > a.problem_title");

            Iterator<Element> it = list.iterator();
            while(it.hasNext()) {
                Element s = it.next();
                String item = s.ownText() + "/" + s.attr("title");
                updatedList.put(item,"");
                Log.d("debug",item);
            }

            Elements check = doc_additional.select("a#prev_page");
            if(check.isEmpty())   return;
            int nextLast = Integer.parseInt(check.attr("href").split("=")[2]);
            recursiveCrawl(nextLast);
        } catch(Exception e) {
            Log.d("debug", "die async");
        }

    }

    public HashMap<String, String> updatedProblemList(final String userName, final int last) {
        this.userName = userName;
        status = "additionalCrawl";

        updatedList = new HashMap<String, String>();
        recursiveCrawl(last);

        return updatedList;
    }
}