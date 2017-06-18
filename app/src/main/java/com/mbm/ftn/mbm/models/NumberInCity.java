package com.mbm.ftn.mbm.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Boris K on 18-Jun-17.
 */
@DatabaseTable(tableName = "number_in_city")
public class NumberInCity extends BaseModel implements Serializable {

    @DatabaseField
    private int numberId;
    @DatabaseField
    private int cityId;

    public NumberInCity() {
    }

    public NumberInCity(int numberId, int cityId) {
        this.numberId = numberId;
        this.cityId = cityId;
    }

    public int getNumberId() {
        return numberId;
    }

    public void setNumberId(int numberId) {
        this.numberId = numberId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
