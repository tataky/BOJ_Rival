package com.example.leeth.boj_rival;

import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnticipateOvershootInterpolator;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.FILENAME;


/**
 * Created by leeth on 2017-08-21.
 */

public class add_user extends AppCompatActivity {

    userInformation currentUser;

    public static final int REQUEST_CODE_ANOTHER = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_input);
        Button b = (Button)findViewById(R.id.button2);
        b.setEnabled(false);
    }

    public void getNewUser(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        EditText edit = (EditText)findViewById(R.id.edittext);
        String userName = edit.getText().toString();
        crawler cr = new crawler();
        TextView v = (TextView)findViewById(R.id.resultview);
        if(!cr.isExist(userName)) {
            String result = ("Cannot find user " + userName);
            v.setText(result.toCharArray(),0,result.length());
            Button b = (Button)findViewById(R.id.button2);
            b.setEnabled(false);
        } else {
            currentUser = cr.crawl(userName);
            String printString = currentUser._id + " : Accepted " + Integer.toString(currentUser.problems.size()) + "\n";
            v.setText(printString.toCharArray(),0,printString.length());
            Button b = (Button)findViewById(R.id.button2);
            b.setEnabled(true);
        }
        return;
    }

    public void addUser(View view) {
        final String fileName = "user_information";

        TextView v = (TextView) findViewById(R.id.resultview);

        JSONParser parser = new JSONParser();
        try {
            Object userInfo = parser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) userInfo;
            if (jsonObject.has(currentUser._id)) {
                String str = "Already exist";
                v.setText(str.toCharArray(),0,str.length());
                return;
            }
            JSONObject obj = new JSONObject();
            obj.put("last", currentUser.last);
            obj.put("problemPool", new JSONObject(currentUser.problems));
            jsonObject.put(currentUser._id, obj);
            FileWriter fo = new FileWriter(fileName);
            fo.write(jsonObject.toString());
            fo.close();
            String result = "Successfully added " + currentUser._id;
            v.setText(result.toCharArray(),0,result.length());
        } catch (org.json.JSONException e) {
            return;
        } catch (java.io.FileNotFoundException e) {
            return;
        } catch (org.json.simple.parser.ParseException e) {
            return;
        } catch (java.io.IOException e) {
            return;
        }
    }
}