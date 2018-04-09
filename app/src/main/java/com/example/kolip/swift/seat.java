package com.example.kolip.swift;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kolip on 09-04-2018.
 */

public class seat {
ArrayList<String> list;

    public seat(ArrayList<String> list) {
        this.list = list;
    }
public seat(){}
    public ArrayList<String> getlist() {
        return list;
    }

    public void setlist(ArrayList<String> list) {
        this.list = list;
    }
}
