package com.jsrwares.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.List;

class Connector extends SQLiteOpenHelper {

    SQLiteDatabase mDb = null;
    private Context mContext;
    private ConnectorTableDefinitions mTableDefs;
    private static Connector mConnector = null;

    private Connector(Context context, String name, ConnectorTableDefinitions tableDefs) {
        super(context, name, null, tableDefs.getVersion());
        mContext = context;
        this.mTableDefs = tableDefs;
    }

    public static Connector getInstance(Context context, String name,
                                        ConnectorTableDefinitions tableDefs) {
        if (mConnector == null) {
            mConnector = new Connector(context.getApplicationContext(), name, tableDefs);
        }
        return mConnector;
    }

    @Override
    public synchronized void close() {
        super.close();
        mDb.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mDb = db;
        String sql = "";
        List<String> createQuerys;
        createQuerys = mTableDefs.getCreateQuerys();

        for (String createQuery : createQuerys) {
            executeUpdate(createQuery, null);
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mDb = db;
        String sql = "";
        // Drop older table if existed
        List<String> tables = mTableDefs.getTables();

        for (String table : tables) {
            sql = mContext.getString(R.string.drop_statement);
            String arg[] = new String[]{table};
            executeUpdate(sql, arg);
        }

        // Create tables again
        onCreate(db);
    }

    // This method exists to have only one try-catch for create, delete, update, insert queries
    public boolean executeUpdate(String sql, @Nullable String[] argList) {
        boolean success = true;
        try {
            if (argList == null)
                mDb.execSQL(sql);
            else
                mDb.execSQL(sql, argList);
        } catch (SQLException e) {
            success = false;
            Utils.writeLog(e, this.toString(), "executeUpdate", sql);
        }
        return success;
    }
}

