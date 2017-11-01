package com.stxrun.zmap.beans;

import android.content.Context;
import android.database.Cursor;

import com.amap.api.maps.model.LatLng;
import com.stxrun.zmap.statics.Static;
import com.stxrun.zmap.utils.DBManager;
import com.stxrun.zmap.utils.L;

/**
 * Created by stxr on 17-9-22.
 * 提供教室的名称，经纬度位置信息
 */

public class Classroom {
    private Context context;
    private String name;
    private double latitude;
    private double longitude;
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

    /**
     * 根据名字的一部分，查找整个名字
     *
     * @param name
     * @return
     */
    public Cursor getNames(String name) {
        Cursor cursor = null;
        cursor = db.query("select _id,name from zzu_classroom where (name like " + "'%" + name + "%')", null);
        return cursor;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据名字得到纬度信息
     *
     * @param name
     * @return
     */
    public double getLatitude(String name) {
        Cursor cursor = db.query("select latitude from zzu_classroom where name like '" + name + "'", null);
        cursor.moveToFirst();
        this.latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("latitude")));
        return this.latitude;
    }

    /**
     * 根据名字得到经度信息
     *
     * @param name
     * @return
     */
    public double getLongtitude(String name) {
        Cursor cursor = db.query("select longitude from zzu_classroom where name like '" + name + "'", null);
        cursor.moveToFirst();
        L.e("getLongitude: " + cursor.getCount());
        this.longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("longitude")));
        return this.longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(this.latitude, this.longitude);
    }

    /**
     * 根据名字得到位置信息
     *
     * @param name
     * @return
     */
    public LatLng getPosition(String name) {
        return latLng = new LatLng(getLatitude(name), getLongtitude(name));
    }

    /**
     * 判断数据库里是否有对应的坐标
     *
     * @param latLng 要查找的坐标
     * @return
     */
    public boolean hasPosition(LatLng latLng) {
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        //截取double前8位，不四舍五入
        String la = String.format("%.8f", latitude);
        Cursor cursor = db.query("select latitude from zzu_classroom where latitude like " + "'" + la + "'", null);
//        L.e("hasPosition: "+cursor.getCount());
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据坐标得到楼层的位置
     *
     * @param latLng 输入的坐标
     * @return
     */
    public String getBuilding(LatLng latLng) {
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        //截取double前8位，四舍五入
        String la = String.format("%.8f", latitude);
        Cursor cursor = db.query("select building from zzu_classroom where latitude like " + "'" + la + "'", null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("building"));
    }

    /**
     * 根据坐标显示楼层图片资源id的数组
     *
     * @param latLng
     * @return
     */
    public int[] getFloorResource(LatLng latLng) {
        String name = getBuilding(latLng);
        if (name.contains("北1")) {
            return Static.CLASSROOM_N1;
        } else if (name.contains("北2东")) {
            return Static.CLASSROOM_N2_E;
        } else if (name.contains("北2西")) {
            return Static.CLASSROOM_N2_W;
        } else if (name.contains("北3东")) {
            return Static.CLASSROOM_N3_E;
        } else if (name.contains("北3西")) {
            return Static.CLASSROOM_N3_W;
        } else if (name.contains("北4")) {
            return Static.CLASSROOM_N4;
        } else if (name.contains("北5")) {
            return Static.CLASSROOM_N5;
        } else if (name.contains("北6")) {
            return Static.CLASSROOM_N6;
        } else if (name.contains("南1")) {
            return Static.CLASSROOM_S1;
        } else if (name.contains("南2东")) {
            return Static.CLASSROOM_S2_E;
        } else if (name.contains("南2西")) {
            return Static.CLASSROOM_S2_W;
        } else if (name.contains("南3东")) {
            return Static.CLASSROOM_S3_E;
        } else if (name.contains("南3西")) {
            return Static.CLASSROOM_S3_W;
        } else if (name.contains("南4")) {
            return Static.CLASSROOM_S4;
        } else if (name.contains("南5")) {
            return Static.CLASSROOM_S5;
        } else if (name.contains("南6")) {
            return Static.CLASSROOM_S6;
        } else if (name.contains("中")) {
            return Static.Z;
        } else {
            return null;
        }
    }
}
