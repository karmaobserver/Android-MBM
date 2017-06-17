package com.mbm.ftn.mbm.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mbm.ftn.mbm.models.City;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.models.NumberList;
import com.mbm.ftn.mbm.models.Profile;
import com.mbm.ftn.mbm.models.SurvivalText;

/**
 * Created by Makso on 5/23/2017.
 */


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "MbmDB.sqlite";

    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 142;

    // the DAO object we use to access the SimpleData table
    //pressure
    private Dao<Number, Integer> numberDao = null;
    private Dao<City, Integer> cityDao = null;
    private Dao<Profile, Integer> profileDao = null;
    private Dao<NumberList, Integer> numberListDao = null;
    private Dao<SurvivalText, Integer> survivalTextDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, City.class);
            TableUtils.createTable(connectionSource, Profile.class);
            TableUtils.createTable(connectionSource, NumberList.class);
            TableUtils.createTable(connectionSource, Number.class);
            TableUtils.createTable(connectionSource, SurvivalText.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

 /*   @Override
    public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            List<String> allSql = new ArrayList<String>();
            switch(oldVersion)
            {
                case 1:
                    //allSql.add("altere AdData add column `new_col` VARCHAR");
                    //allSql.add("altere AdData add column `new_col2` VARCHAR");
            }
            for (String sql : allSql) {
                db.execSQL(sql);
            }
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }

    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, City.class, true);
            TableUtils.dropTable(connectionSource, Profile.class, true);
            TableUtils.dropTable(connectionSource, NumberList.class, true);
            TableUtils.dropTable(connectionSource, Number.class, true);
            TableUtils.dropTable(connectionSource, SurvivalText.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Number, Integer> getNumberDao() {
        if (null == numberDao) {
            try {
                numberDao = getDao(Number.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return numberDao;
    }

    public Dao<City, Integer> getCityDao() {
        if (null == cityDao) {
            try {
                cityDao = getDao(City.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return cityDao;
    }

    public Dao<Profile, Integer> getProfileDao() {
        if (null == profileDao) {
            try {
                profileDao = getDao(Profile.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return profileDao;
    }

    public Dao<SurvivalText, Integer> getSurvivalTextDao() {
        if (null == survivalTextDao) {
            try {
                survivalTextDao = getDao(SurvivalText.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return survivalTextDao;
    }

    public Dao<NumberList, Integer> getNumberListDao() {
        if (null == numberListDao) {
            try {
                numberListDao = getDao(NumberList.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return numberListDao;
    }

}
