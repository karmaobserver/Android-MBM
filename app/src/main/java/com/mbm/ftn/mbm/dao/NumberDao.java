package com.mbm.ftn.mbm.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
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

public class NumberDao implements Crud {

    private DatabaseHelper helper;

    public NumberDao(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    public List<Number> findAllbyListName(String numberListName) {

        List<Number> results = null;

        try {

            QueryBuilder<NumberList, Integer> numberListQb = helper.getNumberListDao().queryBuilder();
            numberListQb.where().eq(NumberList.NUMBERLIST_NAME_FIELD_NAME, numberListName);
            QueryBuilder<Number, Integer> numberQb = helper.getNumberDao().queryBuilder();
            results = numberQb.join(numberListQb).query();
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

        Number object = (Number) item;
        try {
            index = helper.getNumberDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int update(Object item) {

        int index = -1;

        Number object = (Number) item;

        try {
            helper.getNumberDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int delete(Object item) {

        int index = -1;

        Number object = (Number) item;

        try {
            helper.getNumberDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;

    }

    @Override
    public Object findById(int id) {

        Number number = null;
        try {
            number = helper.getNumberDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return number;

    }

    @Override
    public List<Number> findAll() {

        List<Number> items = null;

        try {
            items = helper.getNumberDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

}
