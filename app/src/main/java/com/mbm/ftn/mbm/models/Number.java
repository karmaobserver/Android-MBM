package com.mbm.ftn.mbm.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Makso on 4/25/2017.
 */
@DatabaseTable
public class Number extends BaseModel implements Serializable{

    public static final String NUMBERLIST_FIELD_NAME = "number_list";

    @DatabaseField
    private String title;

    @DatabaseField
    private String number;

    @DatabaseField
    private String description;

    @DatabaseField(foreign=true,foreignAutoRefresh=true, columnName = NUMBERLIST_FIELD_NAME)
    private NumberList numberList;

    @DatabaseField
    private String website;

    @DatabaseField
    private String address;

    public Number() {
    }

    public Number(String title, String number, String description, NumberList numberList, String website, String address) {
        this.title = title;
        this.number = number;
        this.description = description;
        this.numberList = numberList;
        this.website = website;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NumberList getNumberList() {
        return numberList;
    }

    public void setNumberList(NumberList numberList) {
        this.numberList = numberList;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Number{" +
                "title='" + title + '\'' +
                ", number='" + number + '\'' +
                ", description='" + description + '\'' +
                ", numberList=" + numberList +
                ", website='" + website + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
