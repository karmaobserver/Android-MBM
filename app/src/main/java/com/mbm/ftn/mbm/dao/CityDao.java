package com.mbm.ftn.mbm.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mbm.ftn.mbm.database.Crud;
import com.mbm.ftn.mbm.database.DatabaseHelper;
import com.mbm.ftn.mbm.database.DatabaseManager;
import com.mbm.ftn.mbm.models.City;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.models.NumberList;

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

    public List<City> findAllExpectCountry() {

        List<City> results = null;

        try {
            QueryBuilder<City, Integer> cityQb = helper.getCityDao().queryBuilder();

            cityQb.where().ne(City.CITY_NAME_FIELD_NAME, "Srbija");

            results = cityQb.query();
            Log.d("REZULTAT", "JE: " + results.size());
        } catch (SQLException e) {
            Log.d("REZULTAT", "JE: CATCH");
            e.printStackTrace();
        }

        return results;
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
