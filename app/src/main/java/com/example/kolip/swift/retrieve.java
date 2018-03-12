package com.example.kolip.swift;

import java.util.HashMap;

/**
 * Created by kolip on 23-02-2018.
 */

public class retrieve{


        private String username;
        private HashMap<String, Boolean> privacy;

        public retrieve() {}

        public retrieve(String username, HashMap<String, Boolean> privacy) {
            this.username = username;
            this.privacy = privacy;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public HashMap<String, Boolean> getPrivacy() {
            return privacy;
        }

        public void setPrivacy(HashMap<String, Boolean> privacy) {
            this.privacy = privacy;
        }
    }

