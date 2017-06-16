package com.mbm.ftn.mbm.dao;

import android.content.Context;

import com.mbm.ftn.mbm.database.Crud;
import com.mbm.ftn.mbm.database.DatabaseHelper;
import com.mbm.ftn.mbm.database.DatabaseManager;
import com.mbm.ftn.mbm.models.City;
import com.mbm.ftn.mbm.models.Number;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Makso on 6/15/2017.
 */

public class CityDao implements Crud {

    private DatabaseHelper helper;

    public CityDao(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }


    @Override
    public int create(Object item) {

        int index = -1;

        City object = (City) item;
        try {
            index = helper.getCityDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int update(Object item) {

        int index = -1;

        City object = (City) item;

        try {
            helper.getCityDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int delete(Object item) {

        int index = -1;

        City object = (City) item;

        try {
            helper.getCityDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;

    }

    @Override
    public Object findById(int id) {

        City city = null;
        try {
            city = helper.getCityDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;

    }

    @Override
    public List<City> findAll() {

        List<City> items = null;

        try {
            items = helper.getCityDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

}
