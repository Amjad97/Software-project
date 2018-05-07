package com.softwareproject.focus.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.softwareproject.focus.Models.app;
import com.softwareproject.focus.Models.profile;

import java.util.ArrayList;

/**
 * Created by Amjad on 03/04/18.
 */

public class Database extends SQLiteOpenHelper {

    public static final String name = "Database.db";
    Context context;

    public Database(Context context) {

        super(context, name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table profile (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,profile_status TEXT,Days TEXT,"
                + "Times TEXT,repeat TEXT)");
        db.execSQL("create table app ( app_name TEXT,switch_value TEXT, profile_id INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS profile");
        db.execSQL("DROP TABLE IF EXISTS app");
        onCreate(db);
    }

    public void Insert_profile(String name, String status, String days, String times, String repeat) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("profile_status", status);
        values.put("Days", days);
        values.put("Times", times);
        values.put("repeat", repeat);
        db.insert("profile", null, values);

    }

    public void update_status(String name, String s_value) {
        this.getWritableDatabase().execSQL("UPDATE profile SET profile_status='" + s_value + "' WHERE name='" + name + "'");
    }

    public void update_name(String name,int id) {
        this.getWritableDatabase().execSQL("UPDATE profile SET name='" + name + "' WHERE id='" + id + "'");
    }

    public void update_time(String time,int id) {
        this.getWritableDatabase().execSQL("UPDATE profile SET Times='" + time + "' WHERE id='" + id + "'");
    }

    public void update_days(String days,int id) {
        this.getWritableDatabase().execSQL("UPDATE profile SET Days='" + days + "' WHERE id='" + id + "'");
    }

    public void update_profile(int id, String name, String days, String times, String repeat) {
        this.getWritableDatabase().execSQL("UPDATE profile SET name='" + name + "' WHERE id='" + id + "'");
        this.getWritableDatabase().execSQL("UPDATE profile SET Days='" + days + "' WHERE id='" + id + "'");
        this.getWritableDatabase().execSQL("UPDATE profile SET Times='" + times + "' WHERE id='" + id + "'");
        this.getWritableDatabase().execSQL("UPDATE profile SET repeat='" + repeat + "' WHERE id='" + id + "'");
    }

    public ArrayList<profile> getAllProfile() {
        ArrayList<profile> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("select * from profile", null);
        rs.moveToFirst();
        while (!rs.isAfterLast()) {
            int id = rs.getInt(rs.getColumnIndex("id"));
            String profile_name = rs.getString(rs.getColumnIndex("name"));
            String status = rs.getString(rs.getColumnIndex("profile_status"));
            String days = rs.getString(rs.getColumnIndex("Days"));
            String times = rs.getString(rs.getColumnIndex("Times"));
            String repeat = rs.getString(rs.getColumnIndex("repeat"));
            arrayList.add(new profile(id, profile_name, days, times,repeat,status));
            rs.moveToNext();
        }
        return arrayList;
    }

    public void del_profile(int id) {
        this.getWritableDatabase().delete("profile", "id='" + id + "'", null);
    }

    public void Insert_app(String name, String switch_value, int profile_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("app_name", name);
        values.put("switch_value", switch_value);
        values.put("profile_id", profile_id);
        db.insert("app", null, values);
    }

    public ArrayList<app> getAllApp() {
        ArrayList<app> arraylist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("select * from app", null);
        rs.moveToFirst();
        while (!rs.isAfterLast()) {
            String app_name = rs.getString(rs.getColumnIndex("app_name"));
            String switch_value = rs.getString(rs.getColumnIndex("switch_value"));
            int profile_id = rs.getInt(rs.getColumnIndex("profile_id"));
            arraylist.add(new app(app_name, switch_value, profile_id));
            rs.moveToNext();
        }
        return arraylist;
    }

    public ArrayList<app> get_app() {
        ArrayList<app> arraylist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("select app_name, switch_value, profile_id from app Where profile_id=0",
                null);
        rs.moveToFirst();
        while (!rs.isAfterLast()) {
            String app_name = rs.getString(rs.getColumnIndex("app_name"));
            String switch_value = rs.getString(rs.getColumnIndex("switch_value"));
            int profile_id = rs.getInt(rs.getColumnIndex("profile_id"));
            arraylist.add(new app(app_name, switch_value, profile_id));
            rs.moveToNext();
        }
        return arraylist;
    }

    public void update_app_status(String name, String s_value,int profile_id) {
        this.getWritableDatabase().execSQL("UPDATE app SET switch_value='" + s_value + "' WHERE app_name='" + name +
                "' AND profile_id='"+profile_id+"'");
    }

    public void del_app(String name,int profile_id) {
        this.getWritableDatabase().execSQL("DELETE FROM app WHERE app_name='" + name +"' AND profile_id='"+profile_id+"'");
    }
        public void clear_db(){
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS profile");
            db.execSQL("DROP TABLE IF EXISTS app");
            db.execSQL("create table profile (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,profile_status TEXT," +
                    "Duration_from TEXT," + "Duration_to TEXT,repeat_type TEXT)");
            db.execSQL("create table app ( app_name TEXT,app_icon_path TEXT,switch_value TEXT, profile_id INTEGER)");
    }
}