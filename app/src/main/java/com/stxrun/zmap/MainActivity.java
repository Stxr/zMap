package com.stxrun.zmap;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.stxrun.zmap.basic.LocationActivity;
import com.stxrun.zmap.basic.RouteActivity;

import static com.stxrun.zmap.basic.RouteActivity.ROUTE_TYPE_WALK;


public class MainActivity extends AppCompatActivity implements RouteSearch.OnRouteSearchListener, AMap.OnPOIClickListener, AMap.OnMarkerClickListener {
    //显示地图需要的变量
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象
    private Context context;
    private LocationActivity location;
    //标志
    Marker marker;
    //导航
    private AMapNavi aMapNavi;
    private UiSettings mUiSettings;//定义一个UiSettings对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        //显示地图
        mapView = (MapView) findViewById(R.id.map);
        //必须要写
        mapView.onCreate(savedInstanceState);
        //获取地图对象
        aMap = mapView.getMap();
        //地图点击监听
        aMap.setOnPOIClickListener(this);
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        //设置定位
        location = new LocationActivity(getApplicationContext(), aMap);
        //设置定位监听
        aMap.setLocationSource(location);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        //显示比例尺
        settings.setScaleControlsEnabled(true);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);
        //mark点击监听
        aMap.setOnMarkerClickListener(this);

        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.firetwo));
//        myLocationStyle.radiusFillColor(android.R.color.transparent);
//        myLocationStyle.strokeColor(android.R.color.transparent);
        aMap.setMyLocationStyle(myLocationStyle);

        //获取AMapNavi实例
        aMapNavi = AMapNavi.getInstance(getApplicationContext());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
        location.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    //    添加菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_go:
                Toast.makeText(this, "you clicked go", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void onPOIClick(Poi poi) {
        if (marker != null) {
            marker.destroy();
        }
        marker = aMap.addMarker(new MarkerOptions().position(poi.getCoordinate()).title("到" + poi.getName() + "去？"));

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        RouteActivity rout = new RouteActivity(context, aMap, location.getLatLonPoint(), new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude));
        rout.searchRouteResult(ROUTE_TYPE_WALK);
        return true;
    }

}