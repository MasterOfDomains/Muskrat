package com.jsrwares.muskrat.db;

import android.content.Context;
import android.support.v4.util.Pair;

import com.jsrwares.db.ConnectorTableDefinitions;
import com.jsrwares.muskrat.R;

import java.util.ArrayList;
import java.util.List;

public class ContactTableDefinitions implements ConnectorTableDefinitions {

    private static final int VERSION = 1;
    private List<Pair<String, String>> mTables = new ArrayList<>();

    public ContactTableDefinitions(Context context) {
        String createContactTable = context.getString(R.string.create_contact_table);
        Pair<String, String> contactTablePair = new Pair<>("contact", createContactTable);
        addTable(contactTablePair);
    }

    @Override
    public int getVersion() {
        return VERSION;
    }

    @Override
    public void addTable(Pair<String, String> table) {
        mTables.add(table);
    }

    @Override
    public int getTableCount() {
        return mTables.size();
    }

    @Override
    public List<String> getTables() {
        ArrayList<String> tableNames = new ArrayList<>();
        for (Pair table : mTables)
            tableNames.add((String) table.first);
        return tableNames;
    }

    @Override
    public List<String> getCreateQuerys() {
        ArrayList<String> createStatements = new ArrayList<>();
        for (Pair table : mTables)
            createStatements.add((String) table.second);
        return createStatements;
    }
}
