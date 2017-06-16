package com.mbm.ftn.mbm.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Makso on 6/15/2017.
 */

@DatabaseTable(tableName = "city")
public class City extends BaseModel implements Serializable {

    public static final String CITY_NAME_FIELD_NAME = "city_name";

    @DatabaseField(columnName = CITY_NAME_FIELD_NAME)
    private String name;

    @ForeignCollectionField
    ForeignCollection<Number> numbers;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getCityNameFieldName() {
        return CITY_NAME_FIELD_NAME;
    }

    public ForeignCollection<Number> getNumbers() {
        return numbers;
    }

    public void setNumbers(ForeignCollection<Number> numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", numbers=" + numbers +
                '}';
    }
}
