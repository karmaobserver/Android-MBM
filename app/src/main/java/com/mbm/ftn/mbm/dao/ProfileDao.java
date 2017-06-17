package com.mbm.ftn.mbm.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mbm.ftn.mbm.database.Crud;
import com.mbm.ftn.mbm.database.DatabaseHelper;
import com.mbm.ftn.mbm.database.DatabaseManager;
import com.mbm.ftn.mbm.models.City;
import com.mbm.ftn.mbm.models.Profile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makso on 6/17/2017.
 */

public class ProfileDao implements Crud {

        private DatabaseHelper helper;

        public ProfileDao(Context context) {
            DatabaseManager.init(context);
            helper = DatabaseManager.getInstance().getHelper();
        }

        public boolean checkIfTitleExist(String title) {

            boolean titleExist = false;
            List<Profile> profileList = new ArrayList<>();
            try {

                QueryBuilder<Profile, Integer> profileQb = helper.getProfileDao().queryBuilder();

                profileList = profileQb.where().eq(Profile.TITLE_FIELD_NAME, title).query();
                Log.d("LISTA JE", "JE: "+profileList.size());
                if (profileList.size() == 0) {
                    titleExist = false;
                } else {
                    titleExist = true;
                }
            } catch (SQLException e) {
                Log.d("REZULTAT", "JE: CATCH");
                e.printStackTrace();
            }

            return titleExist;
        }

        @Override
        public int create(Object item) {

            int index = -1;

            Profile object = (Profile) item;
            try {
                index = helper.getProfileDao().create(object);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return index;
        }

        @Override
        public int update(Object item) {

            int index = -1;

            Profile object = (Profile) item;

            try {
                helper.getProfileDao().update(object);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return index;
        }

        @Override
        public int delete(Object item) {

            int index = -1;

            Profile object = (Profile) item;

            try {
                helper.getProfileDao().delete(object);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return index;

        }

        @Override
        public Object findById(int id) {

            Profile profile = null;
            try {
                profile = helper.getProfileDao().queryForId(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return profile;

        }

        @Override
        public List<Profile> findAll() {

            List<Profile> items = null;

            try {
                items = helper.getProfileDao().queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return items;
        }


    }
