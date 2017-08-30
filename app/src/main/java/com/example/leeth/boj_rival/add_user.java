package com.example.leeth.boj_rival;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
        final String fileUserId = "userID.txt", fileUserInfo = currentUser._id + ".json";

        TextView v = (TextView) findViewById(R.id.resultview);
        File fId = getFileStreamPath(fileUserId), fInfo = getFileStreamPath(fileUserInfo);
        // if file is not exist, make new empty txt file
        if(!fId.exists()) {
            try {
                FileOutputStream fos = openFileOutput(fileUserId, MODE_PRIVATE);
                fos.write("".getBytes());
                fos.close();
            } catch (Exception e) {
                return;
            }
        }
        if(!fInfo.exists()) {
            try {
                FileOutputStream fos = openFileOutput(fileUserInfo, MODE_PRIVATE);
                fos.write(new JSONObject().toString().getBytes());
                fos.close();
            } catch (Exception e) {
                return;
            }
        }

        ArrayList<String> idSet = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(fileUserId);
            byte[] data = new byte[fis.available()];

            boolean fileNotEmpty = false;
            if (fis.available() != 0) {
                fileNotEmpty = true;
                while (fis.read(data) != -1) {
                }
            }
            String raw = new String(data,0,data.length);
            String[] nameList = new String[0];
            if(fileNotEmpty && !raw.isEmpty()) nameList = raw.split("\n");

            for (String name : nameList) {
                idSet.add(name);
            }
            if (idSet.contains(currentUser._id)) {
                String str = "Already exist";
                v.setText(str.toCharArray(), 0, str.length());
                return;
            }
            FileOutputStream fos = openFileOutput(fileUserId, MODE_APPEND);
            fos.write((currentUser._id+"\n").getBytes());
            fos.close();
        } catch (Exception e) {
            Log.d("debug","addUser");
            return;
        }

        JSONParser parser = new JSONParser();
        try {
            FileInputStream fis = openFileInput(fileUserInfo);
            byte[] data = new byte[fis.available()];
            while(fis.read(data) != -1){}
            fis.close();

            Object userInfo = parser.parse(new String(data));
            JSONObject jsonObject = new JSONObject(userInfo.toString());

            jsonObject.put("last", currentUser.last);
            jsonObject.put("problemPool", new JSONObject(currentUser.problems));

            FileOutputStream fos = openFileOutput(fileUserInfo, MODE_PRIVATE);
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