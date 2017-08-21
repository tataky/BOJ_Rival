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

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by leeth on 2017-08-21.
 */

public class add_user extends AppCompatActivity {

    class user_information {
        String _id;
        boolean ok;
        ArrayList<Integer> problems;
        user_information() {
            ok=true;
        }
        boolean isOk() {
            return ok;
        }
        //TODO
    }

    user_information currentUser;

    public static final int REQUEST_CODE_ANOTHER = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_input);
        Button b = (Button)findViewById(R.id.button2);
        b.setEnabled(false);
    }

    public void crawl(String userName) {
        user_information ui = new user_information();
        //crawl
        currentUser = ui;
        if(!ui.isOk()) {
            return;
        } else {
            /*
            String FILENAME = "user_information";
            String string = "byungsin";
            try {
                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
                try {
                    fos.write(string.getBytes());
                    fos.close();
                } catch (IOException e) {
                    System.exit(1);
                }
            } catch (FileNotFoundException e) {
                System.exit(1);
            }
            */
            return;
        }
    }

    public void getNewUser(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        EditText edit = (EditText)findViewById(R.id.edittext);
        String userName = edit.getText().toString();
        crawl(userName);
        TextView v = (TextView)findViewById(R.id.resultview);
        if(!currentUser.isOk()) {
            String result = ("Cannot find user " + userName);
            v.setText(result.toCharArray(),0,result.length());
            Button b = (Button)findViewById(R.id.button2);
            b.setEnabled(false);
        } else {
            v.setText(userName.toCharArray(),0,userName.length());
            Button b = (Button)findViewById(R.id.button2);
            b.setEnabled(true);
        }
        return;
    }

    public void addUser(View view) {
        //write currentUser to user_information
        String FILENAME = "user_information";
        String string = "byungsin";
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
            try {
                fos.write(string.getBytes());
                fos.close();
            } catch (IOException e) {
                System.exit(1);
            }
        } catch (FileNotFoundException e) {
            System.exit(1);
        }
    }
}