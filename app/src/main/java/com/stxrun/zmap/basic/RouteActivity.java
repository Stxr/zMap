package com.stxrun.zmap.basic;

import android.app.ProgressDialog;
import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.stxrun.zmap.utils.ToastUtil;

import overlay.WalkRouteOverlay;

/**
 * Created by stxr on 17-9-15.
 * 高德地图步行导航封装
 */

public class RouteActivity implements RouteSearch.OnRouteSearchListener {
    public static final int ROUTE_TYPE_DRIVE = 2;
    public static final int ROUTE_TYPE_WALK = 3;
    private Context context;
    private LatLonPoint startPoint, endPoint;
    private RouteSearch routeSearch;
    private AMap aMap;

    public RouteActivity(Context context,AMap aMap, LatLonPoint s, LatLonPoint e) {
        this.context = context;
        this.startPoint = s;
        this.endPoint = e;
        this.aMap = aMap;
        routeSearch = new RouteSearch(context);
        routeSearch.setRouteSearchListener(this);
    }

    public void searchRouteResult(int routeType) {
        if (startPoint == null) {
            ToastUtil.show(context, "起点未设置");
            return;
        }
        if (endPoint == null) {
            ToastUtil.show(context, "终点未设置");
        }
       // showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startPoint, endPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
//            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, null,
//                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
//            routeSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo);
            routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }


    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    //清除屏幕上的标记，并不清除定位蓝点
                    aMap.clear(true);
                    ToastUtil.show(context,"规划路径成功");
                    final WalkPath walkPath = result.getPaths()
                            .get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                            context, aMap, walkPath,
                            result.getStartPos(),
                            result.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                }
            }
        }

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
