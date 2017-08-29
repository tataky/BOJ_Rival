package com.example.leeth.boj_rival;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by leeth on 2017-08-22.
 */

@SuppressWarnings("serial")
public class userInformation implements Serializable {
    // change public to private
    public String _id;
    public HashMap<String, String> problems, newProblems;
    public int last;
    public int updated;

    public userInformation() {
        problems = new HashMap<String, String>();
        newProblems = new HashMap<String, String>();
    }

}