package com.mbm.ftn.mbm.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Boris K on 18-Jun-17.
 */

@DatabaseTable(tableName = "number_in_list")
public class NumberInList extends BaseModel implements Serializable{

    @DatabaseField
    private int numberId;
    @DatabaseField
    private int listId;

    public NumberInList() {
    }

    public NumberInList(int numberId, int listId) {
        this.numberId = numberId;
        this.listId = listId;
    }

    public int getNumberId() {
        return numberId;
    }

    public void setNumberId(int numberId) {
        this.numberId = numberId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}
