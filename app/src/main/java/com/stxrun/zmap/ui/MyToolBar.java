package com.stxrun.zmap.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.amap.api.maps.model.LatLng;
import com.stxrun.zmap.R;
import com.stxrun.zmap.beans.Classroom;
import com.stxrun.zmap.utils.L;
import com.stxrun.zmap.utils.ToastUtil;


/**
 * Created by stxr on 17-9-23.
 * 自定义我的toolbar
 */

public class MyToolBar extends Toolbar {
    private AutoCompleteTextView completeText;
    public SearchView searchView;
    private Classroom classroom;
    private Cursor cursor;
    private MessageIntent msg;

    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setContext(context);
        L.e("toolbar's context: " + context.toString());
        //加载layout
        LayoutInflater.from(context).inflate(R.layout.toolbar, this);
        initView();
        initData(context);
    }

    private void initData(final Context context) {
        classroom = new Classroom(context);
        //输入1个字的时候开始提示
        completeText.setThreshold(1);
        //显示提示下拉列表长度
        completeText.setDropDownHeight(800);
        //设置文字颜色
        completeText.setTextColor(getResources().getColor(android.R.color.white));
        //设置文字大小
        completeText.setTextSize(16);
        //设置hint
        searchView.setQueryHint("请输入要搜索的教室");
        //设置输入监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                ToastUtil.show(getContext(), query);
                //得到教室的坐标
                try {
                    LatLng latLng = classroom.getPosition(query);
//                    ToastUtil.show(getContext(), latLng.toString());
                    msg.classroomIntent(query,latLng);
                    //移除焦点
                    searchView.clearFocus();
                } catch (Exception e) {
                    ToastUtil.show(getContext(),"未找到此地点");
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cursor = classroom.getNames(newText);
                searchView.setSuggestionsAdapter(new SimpleCursorAdapter(getContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        cursor,
                        new String[]{"name"}, //数据字段的数组
                        new int[]{android.R.id.text1},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));
                return false;
            }
        });
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                //ToastUtil.show(getContext(),"select");
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                //将游标移动到对应位置
                cursor.moveToPosition(position);
                //获取名字
                String name = cursor.getString(cursor.getColumnIndex("name"));
                //将补全的显示在搜索栏上
                searchView.setQuery(name, true);
//                ToastUtil.show(getContext(), "click" + name);
                return true;
            }
        });
    }

    private void initView() {
        searchView = findViewById(R.id.search_view);
        //找到autoCompleteText的id
        completeText = findViewById(R.id.search_src_text);
    }
    //设置监听
    public void setLocationIntent(MessageIntent msg) {
        this.msg = msg;
    }
    //传送位置数据的接口
    public interface MessageIntent {
        void classroomIntent(String name,LatLng latLng);
    }

    public Classroom getClassroom() {
        return classroom;
    }
}
