package com.mbm.ftn.mbm.models;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Makso on 5/21/2017.
 */

public abstract class BaseModel implements Serializable {


    public static final String ID_NAME_FIELD_NAME = "id";

    @DatabaseField(generatedId=true, columnName = ID_NAME_FIELD_NAME)
    private Integer id;

    public BaseModel() {
    }

    public BaseModel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
