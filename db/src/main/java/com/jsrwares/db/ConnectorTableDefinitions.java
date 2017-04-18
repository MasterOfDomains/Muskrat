package com.jsrwares.db;

import android.support.v4.util.Pair;

import java.util.List;

public interface ConnectorTableDefinitions {

    /**
     * Database version
     * @return The current Version of the database
     */

    int getVersion();

    /**
     * Adds a table to the Definitions
     * @param table First: table name, Second: SQL create statement
     */

    void addTable(Pair<String, String> table);

    /**
     * Get the number of tables defined in this Connection
     * @return The number of tables.
     */

    int getTableCount();

    /**
     * Get the names of all tables defined in this connection
     * @return A List of Strings of all table names
     */

    List<String> getTables();

    /**
     * Get the SQL create statements used to create all tables defined in this connection
     * @return A List of Strings of all SQL create statements
     */

    List<String> getCreateQuerys();
}