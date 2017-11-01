package com.stxrun.zmap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.stxrun.zmap.MainActivity;
import com.stxrun.zmap.R;

/**
 * Created by stxr on 17-11-1.
 * 引导界面
 */

public class GuideActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //引导页面，延时1000毫秒
        handler.sendEmptyMessageDelayed(100, 500);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    startActivity(intent);
                    //防止按返回键再次回到此界面
                    finish();
                    break;
            }
        }
    };

}
