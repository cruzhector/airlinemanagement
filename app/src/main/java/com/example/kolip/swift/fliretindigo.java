package com.example.kolip.swift;

import java.util.HashMap;

/**
 * Created by kolip on 24-02-2018.
 */

public class fliretindigo {

    private HashMap<String, Object> IGO613;
    private HashMap<String, Object> indigo1;
    private HashMap<String, Object> indigo2;



    public fliretindigo(HashMap<String, Object> IGO613,HashMap<String, Object> indigo1, HashMap<String, Object> indigo2) {
        this.IGO613 = IGO613;
        this.indigo1 = indigo1;
        this.indigo2 = indigo2;

    }
    public fliretindigo() {}

    public HashMap<String, Object> getIGO613() {
        return IGO613;
    }

    public void setIGO613(HashMap<String, Object> IGO613) {
        this.IGO613 = IGO613;
    }

    public HashMap<String, Object> getindigo1() {
        return indigo1;
    }

    public void setindigo1(HashMap<String, Object> indigo1) {
        this.indigo1 = indigo1;
    }

    public HashMap<String, Object> getindigo2() {
        return indigo2;
    }

    public void setindigo2(HashMap<String, Object> indigo2) {
        this.indigo2 = indigo2;
    }
}
