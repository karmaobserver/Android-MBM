package com.mbm.ftn.mbm.models;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Boris K on 25-Apr-17.
 */

public class SurvivalText implements Serializable{

    @DatabaseField
    private String topic;
    @DatabaseField
    private String text;

    public SurvivalText() {
    }

    public SurvivalText(String topic, String text) {
        this.topic = topic;
        this.text = text;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
