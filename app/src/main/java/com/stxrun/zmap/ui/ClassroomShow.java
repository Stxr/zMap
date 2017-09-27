package com.stxrun.zmap.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.stxrun.zmap.R;


/**
 * Created by stxr on 17-9-26.
 */

public class ClassroomShow extends LinearLayout {
    private PhotoView imageView;
    private TextView tv_caption;
    public ClassroomShow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.classroom_display, this);
        initView();
        initData();
    }

    private void initView() {
        imageView = findViewById(R.id.iv_classroom);
        tv_caption = findViewById(R.id.tv_caption);
    }

    private void initData() {
//        imageView.setImageBitmap(BitmapFactory.decodeResource());
//        imageView.set
        Glide.with(getContext()).load(R.drawable.s2_w1f).into(imageView);
    }

    /**
     * 设置图片
     * @param model
     */
    public void setImage(Object model) {
//        imageView.setImageResource((int)model);
        imageView.setImageBitmap(BitmapFactory.decodeStream(getResources().openRawResource((int)model)));
        Glide.with(getContext())
                .load(model)
                .into(imageView);
    }

    /**
     * 设置文字
     * @param text
     */
    public void setText(CharSequence text) {
        tv_caption.setVisibility(View.VISIBLE);
        tv_caption.setText(text);
    }
}
