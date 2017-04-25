package com.mbm.ftn.mbm.models;

/**
 * Created by Makso on 4/25/2017.
 */

public class Number {

    private String text;

    private String number;

    public Number() {
    }

    public Number(String text, String number) {
        this.text = text;
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
