package com.jsrwares.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public abstract class Dao<T> {

    private Connector mConnector;

    protected SQLiteDatabase getCurrentDb() {
        return mConnector.mDb;
    }

    protected Dao(Context context, String name, ConnectorTableDefinitions tableDefs) {
        mConnector = Connector.getInstance(context, name, tableDefs);
        open();
    }

    protected boolean executeUpdate(String sql, @Nullable String[] argList) {
        return mConnector.executeUpdate(sql, argList);
    }

    public void open() throws SQLException {
        mConnector.getWritableDatabase();
    }

    public void close() {
        mConnector.close();
    }

    public int getNextKey(String sql) {
        int nextKey = 0;
        try {
            Cursor cursor = mConnector.mDb.rawQuery(sql, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        if (cursor.getString(0) != null) {
                            nextKey = cursor.getInt(0); //Integer.parseInt(cursor.getString(0));
                        } else {
                            nextKey = 0;
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (SQLException e) {
            nextKey = 1;
            Utils.writeLog(e, this.toString(), "count", sql);
        }

        return nextKey;
    }

    public abstract ArrayList<T> executeQuery(String sql, @Nullable String[] argList);

    public abstract int insert(T t);

    public abstract boolean update(T t);

    public abstract void delete(int id);

    public abstract void deleteAll();

    public abstract T select(int id);

    public abstract ArrayList<T> selectAll();

    public abstract int getCount();
}