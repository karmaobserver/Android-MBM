package com.mbm.ftn.mbm.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Makso on 5/23/2017.
 */

@DatabaseTable(tableName = "number_list")
public class NumberList extends BaseModel implements Serializable {

    public static final String NUMBERLIST_NAME_FIELD_NAME = "number_list_name";

    @DatabaseField(columnName = NUMBERLIST_NAME_FIELD_NAME)
    private String name;

    @ForeignCollectionField
    ForeignCollection<Number> numbers;

    public NumberList() {
    }

    public NumberList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ForeignCollection<Number> getNumbers() {
        return numbers;
    }

    public void setNumbers(ForeignCollection<Number> numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "NumberList{" +
                "name='" + name + '\'' +
                ", numbers=" + numbers +
                '}';
    }
}
