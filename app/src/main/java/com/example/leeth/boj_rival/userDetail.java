package com.example.leeth.boj_rival;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by leeth on 2017-08-29.
 */

public class userDetail extends AppCompatActivity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);

        userInformation prevUi = (userInformation) getIntent().getSerializableExtra("userInfo");
        TextView userIdView=(TextView)findViewById(R.id.user_id);
        userIdView.setText(prevUi._id);
        TextView userScoreView=(TextView)findViewById(R.id.user_score);
        userScoreView.setText(""+prevUi.problems.size());

        LinearLayout ll = (LinearLayout) findViewById(R.id.problem_list);
        crawler cr = new crawler();
        HashMap<String, String> updatedList = cr.updatedProblemList(prevUi._id, prevUi.last);

        Set<String> s = updatedList.keySet();
        Iterator<String> it = s.iterator();

        while(it.hasNext()) {
            String cur = it.next();
            String check = prevUi.problems.get(cur);
            if(check != null)   continue;

            TextView newText = new TextView(this);
            newText.setText(cur);
            newText.setTextSize(30);
            newText.setGravity(1);
            ll.addView(newText);
        }
    }
}
