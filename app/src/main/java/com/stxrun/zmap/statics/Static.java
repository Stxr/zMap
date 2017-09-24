package com.stxrun.zmap.statics;

import android.os.Environment;

/**
 * Created by stxr on 17-9-22.
 * 一些静态变量
 */

public class Static {
    public static final String DATABASE_NAME = "classroom_location.db";
    //包名
    public static final String PACKAGE_NAME = "com.stxrun.zmap";
    public static final String DATABASE_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME + "/classroom_location";
}
