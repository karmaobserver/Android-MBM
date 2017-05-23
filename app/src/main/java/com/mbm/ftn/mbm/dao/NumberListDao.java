package com.mbm.ftn.mbm.dao;

import android.content.Context;

import com.mbm.ftn.mbm.database.Crud;
import com.mbm.ftn.mbm.database.DatabaseHelper;
import com.mbm.ftn.mbm.database.DatabaseManager;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.models.NumberList;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Makso on 5/23/2017.
 */

public class NumberListDao implements Crud {
    private DatabaseHelper helper;

    public NumberListDao(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {

        int index = -1;

        NumberList object = (NumberList) item;
        try {
            index = helper.getNumberListDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int update(Object item) {

        int index = -1;

        NumberList object = (NumberList) item;

        try {
            helper.getNumberListDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int delete(Object item) {

        int index = -1;

        NumberList object = (NumberList) item;

        try {
            helper.getNumberListDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;

    }

    @Override
    public Object findById(int id) {

        NumberList numberList = null;
        try {
            numberList = helper.getNumberListDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberList;

    }

    @Override
    public List<NumberList> findAll() {

        List<NumberList> items = null;

        try {
            items = helper.getNumberListDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
