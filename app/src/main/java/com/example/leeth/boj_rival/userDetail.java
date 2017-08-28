package com.example.leeth.boj_rival;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by leeth on 2017-08-29.
 */

public class userDetail extends AppCompatActivity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        userInformation ui = (userInformation) getIntent().getSerializableExtra("userInfo");
        TextView textView=(TextView)findViewById(R.id.textview);
        Log.d("debug",Integer.toString(ui.last));
    }
}
