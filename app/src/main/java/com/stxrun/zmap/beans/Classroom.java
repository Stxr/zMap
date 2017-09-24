package com.stxrun.zmap.beans;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.amap.api.maps.model.LatLng;
import com.stxrun.zmap.statics.Static;
import com.stxrun.zmap.utils.DBManager;

/**
 * Created by stxr on 17-9-22.
 * 提供教室的名称，经纬度位置信息
 */

public class Classroom {
    private Context context;
    private String name;
    private double latitude;
    private double longtitude;
    private LatLng latLng;
    private DBManager db;

    public Classroom(Context context) {
        this.context = context;
        if (db == null) {
            //open the database
            db = new DBManager();
            db.loadDatabase(this.context, Static.DATABASE_PATH + "/" + Static.DATABASE_NAME);
        }
    }

    public Cursor getNames(String name) {
        Cursor cursor = null;
        cursor = db.query("select _id,name from zzu_classroom where (name like " + "'%" + name + "%')", null);
        return cursor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude(String name) {
        Cursor cursor = db.query("select latitude from zzu_classroom where name like '" + name + "'", null);
        cursor.moveToFirst();
        return Double.parseDouble(cursor.getString(cursor.getColumnIndex("latitude")));
    }

    public double getLongtitude(String name) {
        Cursor cursor = db.query("select longtitude from zzu_classroom where name like '" + name + "'", null);
        cursor.moveToFirst();
        return Double.parseDouble(cursor.getString(cursor.getColumnIndex("longtitude")));
    }

    public LatLng getPosition(String name) {
        return latLng = new LatLng(getLatitude(name), getLongtitude(name));
    }
}
