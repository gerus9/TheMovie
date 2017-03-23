package com.gerus.themovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by gerus-mac on 15/03/17.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private Context mContext;

    public DBHelper(Context context) {
        super(context, ManagerDatabase.DATABASE_NAME, null, ManagerDatabase.DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }


}
