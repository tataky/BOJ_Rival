package com.example.leeth.boj_rival;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by leeth on 2017-08-22.
 */

public class userInformation {
    String _id;
    HashMap<String, String> problems;
    int last;
    userInformation() {
        problems = new HashMap<String, String>();
    }
}