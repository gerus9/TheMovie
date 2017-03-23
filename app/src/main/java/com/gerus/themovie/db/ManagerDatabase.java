package com.gerus.themovie.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by gerus-mac on 15/03/17.
 */

public class ManagerDatabase {

    private File mFile;
    public static final String DATABASE_NAME = "theMovie";
    public static final int DATABASE_VERSION = 1;
    private DBHelper dbHelper;

    private static ManagerDatabase sInstance;

    private ManagerDatabase(Context context) {
        this.dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        mFile = context.getDatabasePath(DATABASE_NAME);
    }

    public static synchronized ManagerDatabase getInstance(Context context) {
        if (sInstance == null) sInstance = new ManagerDatabase(context);
        return sInstance;
    }

    public boolean checkDataBase() {
        return mFile.exists();
    }


    public DBHelper getDbHelper() {
        return dbHelper;
    }



}
