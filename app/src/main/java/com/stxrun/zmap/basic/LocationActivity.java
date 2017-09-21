package com.stxrun.zmap.basic;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;

/**
 * Created by stxr on 17-9-15.
 * 高德地图定位封装
 */

public class LocationActivity extends MyLocationStyle implements AMap.OnMyLocationChangeListener {
    //定位样式
    private MyLocationStyle locationStyle;
    //定位坐标
    private Location location;
    //定位时间间隔
    private long interval;
    //定位蓝点模式
    private int locationType;
    //是否显示小蓝点
    private boolean showLocation;
    //定位到中心的标志位
    private boolean flagLocation=true;

    private Context context;
    private AMap aMap;

    public LocationActivity(Context context, AMap aMap) {
        this.context = context;
        this.aMap = aMap;
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);
        //设置SDK 自带定位消息监听
        aMap.setOnMyLocationChangeListener(this);
        initData();
    }

    private void initData() {
        if (locationStyle == null) {
            locationStyle = new MyLocationStyle();
        }
        //定位时间间隔
        interval=2000;
        //定位、但不会移动到地图中心点，并且会跟随设备移动。
        locationType = LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER;
        //显示小蓝点
        showLocation = true;
    }

    public void start() {
        //定位时间间隔
        setInterval(getInterval());
        //定位、但不会移动到地图中心点，并且会跟随设备移动。
        setLocationType(getLocationType());
        //显示小蓝点
        setShowLocation(isShowLocation());
    }

    /**
     * 定位监听
     * AMap.setOnMyLocationChangeListener();
     *
     * @param location 坐标
     */
    @Override
    public void onMyLocationChange(Location location) {
        if (location != null) {
            this.location = location;
            if (flagLocation) {
                flagLocation = false;
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLatLng(),17));
            }
        }
    }


    /**
     * @return 坐标
     */
    public LatLonPoint getLatLonPoint() {
        return new LatLonPoint(getLatitude(), getLongitude());
    }

    /**
     * 坐标
     *
     * @return
     */
    public LatLng getLatLng() {
        return new LatLng(getLatitude(), getLongitude());
    }

    /**
     * 纬度
     *
     * @return
     */
    public double getLatitude() {
        if (location != null) {
            return location.getLatitude();
        } else {
            return 0.0;
        }
    }

    /**
     * 经度
     *
     * @return
     */
    public double getLongitude() {
        if (location != null) {
            return location.getLongitude();
        } else {
            return 0.0;
        }
    }


    public void setInterval(long interval) {
        this.interval = interval;
        aMap.setMyLocationStyle(locationStyle.interval(interval));
    }

    public long getInterval() {
        return interval;
    }

    public int getLocationType() {
        return locationType;
    }

    /**
     *
     * @param locationType LOCATION_TYPE_SHOW  只定位一次。
     *                     LOCATION_TYPE_LOCATE 定位一次，且将视角移动到地图中心点。
     *                     LOCATION_TYPE_FOLLOW 连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）。
     *                     LOCATION_TYPE_MAP_ROTATE 连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）。
     *                     LOCATION_TYPE_LOCATION_ROTATE 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
     *                     LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER 连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
     *                     LOCATION_TYPE_FOLLOW_NO_CENTER 连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
     *                     LOCATION_TYPE_MAP_ROTATE_NO_CENTER 连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
     *
     */
    public void setLocationType(int locationType) {
        this.locationType = locationType;
        aMap.setMyLocationStyle(locationStyle.myLocationType(locationType));
    }

    public boolean isShowLocation() {
        return showLocation;
    }

    public void setShowLocation(boolean showLocation) {
        this.showLocation = showLocation;
        aMap.setMyLocationStyle(locationStyle.showMyLocation(showLocation));
    }
}
