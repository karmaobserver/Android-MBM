package com.mbm.ftn.mbm.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Boris K on 25-Apr-17.
 */

@DatabaseTable(tableName = "survival_text")
public class SurvivalText extends BaseModel implements Serializable{

   // public static final String SURVIVALTEXT_TOPIC_FIELD_NAME = "survival_text_name";

   // @DatabaseField(columnName = SURVIVALTEXT_TOPIC_FIELD_NAME)
    @DatabaseField
    private String topic;
    @DatabaseField
    private String text;
    @DatabaseField
    private String description;
    @DatabaseField
    private int image;

    public SurvivalText() {
    }

    public SurvivalText(String topic, String text, String description, int image) {
        this.topic = topic;
        this.text = text;
        this.description = description;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
