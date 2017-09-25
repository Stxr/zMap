package com.stxrun.zmap.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.stxrun.zmap.R;



/**
 * Created by stxr on 17-9-25.
 */

public class FloorPicker extends LinearLayout {
    private NumberPicker numberPicker;

    public FloorPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.floor_picker, this);
        initView();
        initData();

    }


    private void initData() {
        String[] floors = {"1楼", "2楼", "3楼", "4楼"};
        numberPicker.setDisplayedValues(floors);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(2);
        numberPicker.setClickable(false);
//        numberPicker.
        //组织键盘弹出来
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }

    private void initView() {
        numberPicker = findViewById(R.id.number_picker);
    }

}
