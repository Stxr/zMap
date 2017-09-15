package com.stxrun.zmap.basic;

import android.content.Context;
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
import com.amap.api.services.core.LatLonPoint;

/**
 * Created by stxr on 17-9-15.
 */

public class LocationActivity implements LocationSource, AMapLocationListener {
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private AMapLocation nowLocation;
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private Context context;
    private boolean isFirstLoc=true;//标识，用于判断是否只显示一次定位信息和用户重新定位
    private AMap aMap;

    public LocationActivity(Context context, AMap aMap) {
        this.context = context;
        this.aMap = aMap;
        locationClient = new AMapLocationClient(context);
        locationClient.setLocationListener(this);
        initLocationOption();
        locationClient.setLocationOption(locationOption);
        //启动定位
        locationClient.startLocation();
    }

    private void initLocationOption() {
        //初始化定位参数
        locationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        locationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        locationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        locationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(2000);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                nowLocation = new AMapLocation(aMapLocation);
                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
                    //添加图钉
                    //aMap.addMarker(getMarkerOptions(aMapLocation));
                    isFirstLoc = false;
                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());

                Toast.makeText(context, "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onDestroy() {
        if (locationClient != null) {
            locationClient.onDestroy();
        }
    }

    /**
     *
     * @return 坐标
     */
    public LatLonPoint getLatLonPoint() {
        if (nowLocation != null) {
            return new LatLonPoint(nowLocation.getLatitude(), nowLocation.getLongitude());
        }else{
            return null;
        }
    }

}
