package com.stxrun.zmap.ui;


import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.stxrun.zmap.R;
import com.stxrun.zmap.utils.L;
import com.stxrun.zmap.utils.ToastUtil;


/**
 * Created by stxr on 17-9-25.
 */

public class FloorPicker extends LinearLayout implements NumberPicker.OnValueChangeListener {
    private NumberPicker numberPicker;
    private OnValueChangeListener listener;
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
        numberPicker.setOnValueChangedListener(this);
//        numberPicker.
        //组织键盘弹出来
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }

    private void initView() {
        numberPicker = findViewById(R.id.number_picker);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        this.listener.onValueChange(numberPicker, oldVal, newVal);
    }

    public void setOnvalueChangeListener(OnValueChangeListener listener) {
        this.listener = listener;
    }
    public interface OnValueChangeListener {
        void onValueChange(NumberPicker numberPicker, int oldVal, int newVal);
    }
}
