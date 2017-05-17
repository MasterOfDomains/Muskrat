package com.jsrwares.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.List;

class Connector extends SQLiteOpenHelper {

    SQLiteDatabase db = null;
    private Context context;
    private ConnectorTableDefinitions tableDefs;
    private static Connector connector = null;

    private Connector(Context context, String name, ConnectorTableDefinitions tableDefs) {
        super(context, name, null, tableDefs.getVersion());
        this.context = context;
        this.tableDefs = tableDefs;
    }

    public static Connector getInstance(Context context, String name,
                                        ConnectorTableDefinitions tableDefs) {
        if (connector == null) {
            connector = new Connector(context.getApplicationContext(), name, tableDefs);
        }
        return connector;
    }

    @Override
    public synchronized void close() {
        super.close();
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String sql = "";
        List<String> createQuerys;
        createQuerys = tableDefs.getCreateQuerys();

        for (String createQuery : createQuerys) {
            executeUpdate(createQuery, null);
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.db = db;
        String sql = "";
        // Drop older table if existed
        List<String> tables = tableDefs.getTables();

        for (String table : tables) {
            sql = context.getString(R.string.drop_statement);
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
                db.execSQL(sql);
            else
                db.execSQL(sql, argList);
        } catch (SQLException e) {
            success = false;
            Utils.writeLog(e, this.toString(), "executeUpdate", sql);
        }
        return success;
    }
}

