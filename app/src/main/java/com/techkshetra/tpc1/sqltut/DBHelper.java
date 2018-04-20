package com.techkshetra.tpc1.sqltut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "my_db";
    private static final String TABLE = "contacts";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String AGE = "age";

    public DBHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+TABLE+" (id integer primary key, "+NAME+" text, "+EMAIL+" text, "+PHONE+" text,"+AGE+" integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion==1)
            db.execSQL("alter table "+TABLE+" add column "+AGE+" integer");
    }

    public boolean insertcontact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(NAME, contact.getName());
            contentValues.put(EMAIL, contact.getEmail());
            contentValues.put(PHONE, contact.getPhone());
            contentValues.put(AGE, contact.getAge());
            db.insert(TABLE, null, contentValues);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    public boolean updatecontact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(NAME, contact.getName());
            contentValues.put(EMAIL, contact.getEmail());
            contentValues.put(PHONE, contact.getPhone());
            contentValues.put(AGE, contact.getAge());
            db.update(TABLE, contentValues,"id= ?",new String[]{String.valueOf(contact.getId())});
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletecontact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TABLE, "id= ?",new String[]{String.valueOf(contact.getId())});
            return true;

        } catch (Exception e) {
            return false;
        }
    }


    public ArrayList<Contact> getAllContacts() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        try {
            Cursor cur = db.rawQuery("SELECT * FROM " + TABLE, null);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                Contact c = new Contact();
                c.setId(cur.getInt(cur.getColumnIndex("id")));
                c.setName(cur.getString(cur.getColumnIndex(NAME)));
                c.setEmail(cur.getString(cur.getColumnIndex(EMAIL)));
                c.setPhone(cur.getString(cur.getColumnIndex(PHONE)));
                c.setPhone(cur.getString(cur.getColumnIndex(AGE)));
                contacts.add(c);
                cur.moveToNext();
            }
            return contacts;
        } catch (Exception e) {
            return null;
        }
    }
}
