package com.example.leeth.boj_rival;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


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
            v.setText(result.toCharArray(), 0, result.length());
            Button b = (Button)findViewById(R.id.button2);
            b.setEnabled(false);
        } else {
            currentUser = cr.crawl(userName);
            String printString = currentUser._id + " : Accepted " + Integer.toString(currentUser.problems.size()) + "\n";
            v.setText(printString.toCharArray(), 0, printString.length());
            Button b = (Button)findViewById(R.id.button2);
            b.setEnabled(true);
        }
        return;
    }

    public void addUser(View view) {
        final String fileName = "user_information";

        TextView v = (TextView) findViewById(R.id.resultview);
        File f = getFileStreamPath(fileName);

        // if file is not exist, make new empty json file
        if(!f.exists()) {
            try {
                FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
                JSONObject obj = new JSONObject();
                fos.write((obj.toString()).getBytes());
                fos.close();
            } catch (Exception e) {
                return;
            }
        }

        JSONParser parser = new JSONParser();
        try {
            FileInputStream fis = openFileInput(fileName);
            byte[] data = new byte[fis.available()];
            while(fis.read(data) != -1){}
            fis.close();

            Object userInfo = parser.parse(new String(data));
            JSONObject jsonObject = new JSONObject(userInfo.toString());

            if (jsonObject.has(currentUser._id)) {
                String str = "Already exist";
                v.setText(str.toCharArray(), 0, str.length());
                return;
            }

            JSONObject obj = new JSONObject();
            obj.put("last", currentUser.last);
            obj.put("problemPool", new JSONObject(currentUser.problems));
            jsonObject.put(currentUser._id, obj);

            // change : not mode_append
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(jsonObject.toString().getBytes());
            fos.close();

            // how about making this toast?
            String result = "Successfully added " + currentUser._id;
            v.setText(result.toCharArray(), 0, result.length());
        } catch (Exception e) {
            return;
        }
    }
}