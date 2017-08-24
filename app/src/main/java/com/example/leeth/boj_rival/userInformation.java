package com.example.leeth.boj_rival;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by leeth on 2017-08-22.
 */

public class userInformation {
    // change public to private
    public String _id;
    public HashMap<String, String> problems;
    public int last;

    public userInformation() {
        problems = new HashMap<String, String>();
    }
}