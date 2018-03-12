package com.example.kolip.swift;

import java.util.HashMap;

/**
 * Created by kolip on 23-02-2018.
 */

public class fliret {


    private String username;
    private HashMap<String, Object> AIR319;
    private HashMap<String, Object> AIR367;
    private HashMap<String, Object> ATR12;
    public fliret() {}

    public fliret(String username, HashMap<String, Object> AIR319,HashMap<String, Object> AIR367,HashMap<String, Object> ATR12) {
        this.username = username;
        this.AIR319 = AIR319;
        this.AIR367 = AIR367;
        this.ATR12 = ATR12;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<String, Object> getAIR319() {
        return AIR319;
    }

    public void setAIR319(HashMap<String, Object> AIR319) {
        this.AIR319 = AIR319;
    }

    public HashMap<String, Object> getAIR367() {
        return AIR367;
    }

    public void setAIR367(HashMap<String, Object> AIR367) {
        this.AIR367 = AIR367;
    }


    public HashMap<String, Object> getATR12() {
        return ATR12;
    }

    public void setATR12(HashMap<String, Object> ATR12) {
        this.ATR12 = ATR12;
    }
}
