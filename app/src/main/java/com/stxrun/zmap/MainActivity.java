package com.stxrun.zmap;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Poi;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.stxrun.zmap.basic.LocationActivity;
import com.stxrun.zmap.basic.Navi;
import com.stxrun.zmap.basic.RouteActivity;
import com.stxrun.zmap.ui.ClassroomShow;
import com.stxrun.zmap.ui.FloorPicker;
import com.stxrun.zmap.ui.MyToolBar;
import com.stxrun.zmap.utils.L;

import static com.stxrun.zmap.basic.RouteActivity.ROUTE_TYPE_WALK;


public class MainActivity extends AppCompatActivity implements RouteSearch.OnRouteSearchListener, AMap.OnPOIClickListener, AMap.OnInfoWindowClickListener {
    //显示地图需要的变量
    private MapView mapView;//地图控件
    private RelativeLayout isNavi;
    private AMap aMap;//地图对象
    private Context context;
    private LocationActivity location;
    private LinearLayout ll_navi;
    private MyToolBar toolbar;
    private TextView tv_dest;
    private FloorPicker floorPicker;
    private ClassroomShow classroomShow;
    //标志
    Marker marker;
    //导航

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        //必须要写
        mapView.onCreate(savedInstanceState);
        initData();
    }

    private void initView() {
        context = getApplicationContext();
        L.e("main context: " + context.toString());
        //显示地图
        mapView = (MapView) findViewById(R.id.map);
        //获取地图对象
        aMap = mapView.getMap();
        isNavi = (RelativeLayout) findViewById(R.id.rl_is_navi);
        ll_navi = (LinearLayout) findViewById(R.id.ll_detail);
        toolbar = (MyToolBar) findViewById(R.id.layout_toolbar);
        tv_dest = (TextView) findViewById(R.id.tv_destination);
        floorPicker = (FloorPicker) findViewById(R.id.floor_picker);
        classroomShow = (ClassroomShow) findViewById(R.id.classroom_show);
        setSupportActionBar(toolbar);
    }

    private void initData() {
        //地图点击监听
        aMap.setOnPOIClickListener(this);
        //mark窗口点击监听
        aMap.setOnInfoWindowClickListener(this);
        //触摸监听
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                isNavi.setVisibility(View.GONE);
                classroomShow.setVisibility(View.GONE);
            }
        });
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        //显示比例尺
        settings.setScaleControlsEnabled(true);

        ll_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navi navi = new Navi(MainActivity.this);
                navi.setStart("我的位置", location.getLatLng());
                navi.setEnd(marker.getTitle(), marker.getPosition());
                navi.onCreate();
            }
        });
        //设置定位
        location = new LocationActivity(getApplicationContext(), aMap);
        //开始定位
        location.start();
        toolbar.setLocationIntent(new MyToolBar.MessageIntent() {
            @Override
            public void classroomIntent(String name, LatLng latLng) {
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                resetMarker(name, null, latLng);
//                ToastUtil.show(MainActivity.this, "这是MainActivity的坐标:" + latLng.toString());
            }
        });
        floorPicker.setOnvalueChangeListener(new FloorPicker.OnValueChangeListener() {
            //just a test
            int[] id = {R.drawable.n1_1f,R.drawable.n1_2f,R.drawable.n1_3f};
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                classroomShow.setVisibility(View.VISIBLE);
                classroomShow.setImage(id[newVal]);
            }
        });
//        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                if (marker != null) {
//                    marker.destroy();
//                }
//                marker = aMap.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
//
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
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
    protected void onRestart() {
        super.onRestart();
        //回来是取消焦点
        toolbar.searchView.clearFocus();
    }

    @Override
    public void onPOIClick(Poi poi) {

        resetMarker(poi.getName(), null, poi.getCoordinate());
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //先清除界面上的标志，
        aMap.clear(true);
        //开始导航
        RouteActivity rout = new RouteActivity(context, aMap, location.getLatLonPoint(), new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude));
        rout.searchRouteResult(ROUTE_TYPE_WALK);
        isNavi.setVisibility(View.VISIBLE);
    }

    private void resetMarker(String name, String snippet, LatLng latLng) {
        if (marker != null) {
            marker.destroy();
        }
        marker = aMap.addMarker(new MarkerOptions().title("到" + name + "去？").position(latLng).snippet(snippet));
        floorPicker.setVisibility(View.VISIBLE);
        //设置目的地提示
        tv_dest.setText(name);
        //默认显示标题
        marker.showInfoWindow();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
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
}