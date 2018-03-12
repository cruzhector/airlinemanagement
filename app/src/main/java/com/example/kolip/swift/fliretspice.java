package com.example.kolip.swift;

import java.util.HashMap;

/**
 * Created by kolip on 24-02-2018.
 */

public class fliretspice {



    private HashMap<String, Object> SPC143;
    private HashMap<String, Object> SPBUS43;
    private HashMap<String, Object> SPI123;

    public fliretspice(HashMap<String, Object> SPC143, HashMap<String, Object> SPBUS43, HashMap<String, Object> SPI123) {
        this.SPC143 = SPC143;
        this.SPBUS43 = SPBUS43;
        this.SPI123 = SPI123;
    }

    public fliretspice() {}

    public HashMap<String, Object> getSPC143() {
        return SPC143;
    }

    public void setSPC143(HashMap<String, Object> SPC143) {
        this.SPC143 = SPC143;
    }

    public HashMap<String, Object> getSPBUS43() {
        return SPBUS43;
    }

    public void setSPBUS43(HashMap<String, Object> SPBUS43) {
        this.SPBUS43 = SPBUS43;
    }

    public HashMap<String, Object> getSPI123() {
        return SPI123;
    }

    public void setSPI123(HashMap<String, Object> SPI123) {
        this.SPI123 = SPI123;
    }
}
