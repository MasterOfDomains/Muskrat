package com.jsrwares.muskrat.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.support.annotation.Nullable;

import com.jsrwares.db.Dao;
import com.jsrwares.db.Utils;
import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.contacts.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactDao extends Dao<Contact> {

    Context mContext;

    public ContactDao(Context context) {
        super(context, context.getString(R.string.database_name),
                new ContactTableDefinitions(context));
        mContext = context;
    }

    public Integer getNextKey() {
        Integer nextKey = 0;
        String sql = mContext.getString(R.string.contact_next_key);
        nextKey = getNextKey(sql);
        return nextKey;
    }

    public ArrayList<Contact> selectQueryString(String queryString) {
        ArrayList<Contact> contacts;
        String sql = mContext.getString(R.string.select_all_contacts);
        ArrayList<String> likeColumns = new ArrayList<>();
        likeColumns.add("nameFirst");
        likeColumns.add("nameLast");
        ArrayList<String> orderColumns = new ArrayList<>();
        orderColumns.add("nameLast");
        Utils.whereLikeQuery(sql, likeColumns, queryString);
        Utils.orderQuery(sql, orderColumns, false);
        contacts = executeQuery(sql, null);
        return contacts;
    }

    @Override
    public ArrayList<Contact> executeQuery(String sql, @Nullable String[] argList) {
        ArrayList<Contact> contacts;
        Cursor cursor;

        try {
            cursor = getCurrentDb().rawQuery(sql, argList);
            if (cursor != null) {
                contacts = populateContactList(cursor);
            } else {
                contacts = new ArrayList<Contact>();
            }
            if (cursor != null)
                cursor.close();
        } catch (SQLException e) {
            contacts = new ArrayList<Contact>();
            Utils.writeLog(e, this.toString(), "executeQuery", sql);
        }

        return contacts;
    }

    private ArrayList<Contact> populateContactList(Cursor cursor) {
        ArrayList<Contact> contacts;
        contacts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex("id")));
                contact.setFirstName(cursor.getString(cursor.getColumnIndex("nameFirst")));
                contact.setLastName(cursor.getString(cursor.getColumnIndex("nameLast")));
                contact.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                contact.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return contacts;
    }

    @Override
    public int insert(Contact contact) {
        int returnVal = -1;
        if (contact.getId() == null) {
            Integer nextKey = getNextKey();
            returnVal = nextKey;
            contact.setId(nextKey);
        }

        String sql = mContext.getString(R.string.insert_contact);
        String args[] = new String[5];
        args[0] = contact.getId().toString();
        args[1] = contact.getFirstName();
        args[2] = contact.getLastName();
        args[3] = contact.getEmail();
        args[4] = contact.getPhone();
        executeUpdate(sql, args);
        return returnVal;
    }

    @Override
    public boolean update(Contact contact) {
        String sql = mContext.getString(R.string.update_contact);
        String args[] = new String[5];
        args[0] = Utils.sqlFormatValue(contact.getFirstName(), true);
        args[1] = Utils.sqlFormatValue(contact.getLastName(), true);
        args[2] = Utils.sqlFormatValue(contact.getEmail(), true);
        args[3] = Utils.sqlFormatValue(contact.getPhone(), true);
        args[4] = Integer.toString(contact.getId());
        return executeUpdate(sql, args);
    }

    @Override
    public void delete(int id) {
        String sql = mContext.getString(R.string.delete_contact);
        String arg[] = new String[1];
        arg[0] = Integer.toString(id);
        executeUpdate(sql, arg);
    }

    @Override
    public void deleteAll() {
        String sql = mContext.getString(R.string.delete_all_contacts);
        executeUpdate(sql, null);
    }

    @Override
    public Contact select(int id) {
        List<Contact> contacts;
        String sql = mContext.getString(R.string.select_one_contact);
        String arg[] = new String[1];
        arg[0] = Integer.toString(id);
        contacts = executeQuery(sql, arg);
        Contact contact = null;
        if (contacts.size() > 0)
            contact = contacts.get(0);
        return contact;
    }

    @Override
    public ArrayList<Contact> selectAll() {
        ArrayList<Contact> contacts;
        String sql = mContext.getString(R.string.select_all_contacts);
//        String orderby = " ORDER BY contact.nameLast, contact.nameFirst ASC";
//        sql = sql + orderby;
        ArrayList<String> orderColumns = new ArrayList<>();
        orderColumns.add("contact.nameLast");
        orderColumns.add("contact.nameFirst");
        sql = Utils.orderQuery(sql, orderColumns, false);
        contacts = executeQuery(sql, null);
        contacts.size();
        return contacts;
    }

    // Gets number of records in the table
    @Override
    public int getCount() {
        int counter = 0;
        String sql = mContext.getString(R.string.select_all_contacts);
        try {
            Cursor cursor = getCurrentDb().rawQuery(sql, null);
            counter = cursor.getCount();
            cursor.close();
        } catch (SQLException e) {
            Utils.writeLog(e, this.toString(), "count", sql);
            counter = 0;
        }

        return counter;
    }
}
