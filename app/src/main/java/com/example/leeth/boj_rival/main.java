package com.example.leeth.boj_rival;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Intent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, add_user.class);
                startActivity(intent);
            //    Toast.makeText(getApplicationContext(),"Hello World!",Toast.LENGTH_SHORT).show();
 //               Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
   //                     .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //refresh
        try {
            String FILENAME = "user_information";
            FileInputStream fis = openFileInput(FILENAME);
            try {
                byte[] readBuffer = new byte[fis.available()];
                while (fis.read(readBuffer) != -1) {
                }
                Toast.makeText(getApplicationContext(), new String(readBuffer), Toast.LENGTH_LONG).show();
                fis.close();
            } catch (IOException e) {
                System.exit(1);
            }
        } catch (FileNotFoundException e) {
            System.exit(1);
        }
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
}
