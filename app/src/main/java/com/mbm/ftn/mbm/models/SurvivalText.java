package com.mbm.ftn.mbm.models;

/**
 * Created by Boris K on 25-Apr-17.
 */

public class SurvivalText {

    private String topic;
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
