/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.TVG;

/**
 *
 * @author Taylan
 */
public class ColumnFilterMapByHeader {

    String key;
    String header;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ColumnFilterMapByHeader(String key, String header) {
        this.key = key;
        this.header = header;
    }

}
