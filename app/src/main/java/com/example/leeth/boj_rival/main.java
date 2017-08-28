package com.example.leeth.boj_rival;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static android.R.attr.key;

public class main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String fileName = "problemName";
        TextView v = (TextView) findViewById(R.id.resultview);
        File f = getFileStreamPath(fileName);

        // if file is not exist, make new empty json file
        if(!f.exists()) {
            try {
                FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
                fos.write("".getBytes());
                fos.close();
            } catch (Exception e) {
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rivalListButton = new ArrayList<Button>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, add_user.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("user_information"));
            String line;
            while ((line = br.readLine()) != null) {
                Toast.makeText(getApplicationContext(),line,Toast.LENGTH_LONG).show();
            }
        } catch (java.io.IOException e) {
        }

        //refresh
        resetUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Button> rivalListButton;

    private void deleteUserButton() {
        for(Button button: rivalListButton) {
            button.setVisibility(View.GONE);
        }
        rivalListButton.clear();
    }

    private void makeUserButton() {
        try {
            final String fileName = "user_information";
            JSONParser parser = new JSONParser();

            FileInputStream fis = openFileInput(fileName);
            byte[] data = new byte[fis.available()];
            while(fis.read(data) != -1){}
            fis.close();

            Object obj = parser.parse(new String(data));
            JSONObject jsonObject = new JSONObject(obj.toString());

            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                final String userName = keys.next();
                Log.d("debug",userName);
                Button newButton = new Button(this);
                newButton.setText(userName.toCharArray(), 0, userName.length());
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(main.this, userDetail.class);
                        userInformation ui = new userInformation();
                        JSONParser parser = new JSONParser();
                        try {
                            FileInputStream fis = openFileInput(fileName);
                            byte[] data = new byte[fis.available()];
                            while (fis.read(data) != -1) {
                            }
                            fis.close();

                            Object userInfo = parser.parse(new String(data));
                            JSONObject jo = new JSONObject(userInfo.toString());
                            JSONObject info = jo.getJSONObject(userName);
                            ui._id = userName;
                            ui.last = info.getInt("last");
                            JSONObject problemPool = info.getJSONObject("problemPool");
                            Iterator<String> it = problemPool.keys();
                            while (it.hasNext()) {
                                ui.problems.put(it.next(), "");
                            }
                        } catch (Exception e) {
                            return;
                        }
                        intent.putExtra("userInfo", ui);
                        startActivity(intent);
                    }
                });
                newButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        deleteUser(userName);
                                        resetUI();
                                        break;
                                    case DialogInterface.BUTTON_POSITIVE:
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setMessage("Are you sure?").setNegativeButton("Yes", dialogClickListener)
                                .setPositiveButton("No", dialogClickListener).show();

                        return true;
                    }
                });

                LinearLayout ll = (LinearLayout) findViewById(R.id.user_list);
                Toolbar.LayoutParams lp = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                ll.addView(newButton, lp);

                rivalListButton.add(newButton);
            }
        } catch (Exception e) {
        }
    }

    void resetUI() {
        deleteUserButton();
        makeUserButton();
    }

    public void deleteUser(String userName) {
        final String fileName = "user_information";
        JSONParser parser = new JSONParser();

        try {
            FileInputStream fis = openFileInput(fileName);
            byte[] data = new byte[fis.available()];
            while(fis.read(data) != -1){}
            fis.close();

            JSONObject jsonObject = new JSONObject(parser.parse(new String(data)).toString());

            jsonObject.remove(userName);

            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(jsonObject.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            return;
        }
    }
}
