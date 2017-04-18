package com.jsrwares.db;

import android.util.Log;

import java.util.ArrayList;

public class Utils {

    // TODO Javadoc comments
    // TODO Test all of these

    public static void writeLog(Exception e, String className,
                         String methodName, String sql) {
        String message = "Class : " + className +
                " Method : " + methodName +
                " Query : " + sql +
                " Exception : " + e.getStackTrace().toString();
        Log.e("Dao.java", message);
    }

    public static String sqlFormatValue(Object obj, boolean isQuoted) {
        String returnVal = "";
        if (obj == null)
            returnVal = "null";
        else {
            if (isQuoted) {
                String strValue = obj.toString();
                strValue = strValue.replace("'", "''");
                returnVal = "'" + strValue + "'";
            } else
                returnVal = obj.toString();
        }
        return returnVal;
    }

    public static String orderQuery(String sql, ArrayList<String> columns, boolean descending) {
        sql += " ORDER BY ";
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0)
                sql += ", ";
            sql += columns.get(i);
        }
        if (descending)
            sql += " DESC";
        else
            sql += " ASC";
        return sql;
    }

    public static String whereLikeQuery(String sql, ArrayList<String> columns, String queryString) {
        if (sql.contains("WHERE"))
            sql += " AND ";
        else
            sql += " WHERE ";
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0)
                sql += " || ";
            sql += columns.get(i);
        }
        sql += " LIKE '%" + queryString + "%'";
        return sql;
    }
}
