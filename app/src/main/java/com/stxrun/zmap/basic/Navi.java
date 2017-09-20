package com.stxrun.zmap.basic;

import android.content.Context;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

/**
 * Created by stxr on 17-9-20.
 * 一个非常简单的导航组件的封装，只需写入起点，终点坐标然后调用onCreate方法就行
 */

public class Navi implements INaviInfoCallback {
    private Context context;
    private Poi start;
    private Poi end;
    public Navi(Context context) {
        this.context = context;
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    public Poi getStart() {
        return start;
    }
    public void setStart(String name,LatLng latLng) {
        this.start = new Poi(name,latLng,"");
    }

    public Poi getEnd() {
        return end;
    }

    public void setEnd(String name,LatLng latLng) {
        this.end =  new Poi(name,latLng,"");;
    }

    public void onCreate() {
        AmapNaviPage.getInstance().showRouteActivity(context,
                new AmapNaviParams(getStart(),null,getEnd(), AmapNaviType.WALK),
                Navi.this);
    }
}
