package com.mbm.ftn.mbm.dao;

import android.content.Context;

import com.mbm.ftn.mbm.database.Crud;
import com.mbm.ftn.mbm.database.DatabaseHelper;
import com.mbm.ftn.mbm.database.DatabaseManager;
import com.mbm.ftn.mbm.models.NumberInCity;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boris K on 18-Jun-17.
 */

public class NumberInCItyDao implements Crud{

    private DatabaseHelper helper;

    public NumberInCItyDao(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {

        int index = -1;

        NumberInCity object = (NumberInCity) item;
        try {
            index = helper.getNumberInCityDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int update(Object item) {

        int index = -1;

        NumberInCity object = (NumberInCity) item;

        try {
            helper.getNumberInCityDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int delete(Object item) {

        int index = -1;

        NumberInCity object = (NumberInCity) item;

        try {
            helper.getNumberInCityDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;

    }

    @Override
    public Object findById(int id) {

        NumberInCity nil = null;
        try {
            nil = helper.getNumberInCityDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nil;

    }

    @Override
    public List<NumberInCity> findAll() {

        List<NumberInCity> items = null;

        try {
            items = helper.getNumberInCityDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
