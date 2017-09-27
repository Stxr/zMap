package com.stxrun.zmap.statics;

import android.os.Environment;

import com.stxrun.zmap.R;

/**
 * Created by stxr on 17-9-22.
 * 一些静态变量
 */

public class Static {
    //数据库的名字
    public static final String DATABASE_NAME = "classroom_location.db";
    //包名
    public static final String PACKAGE_NAME = "com.stxrun.zmap";
    //数据库的路径
    public static final String DATABASE_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME + "/classroom_location";
    //北1
    public static final int[] CLASSROOM_N1 = {
            R.drawable.n1_1f, R.drawable.n1_2f, R.drawable.n1_3f, R.drawable.n1_4f
    };
    //北2西
    public static final int[] CLASSROOM_N2_W = {
            R.drawable.n2_w1f, R.drawable.n2_w2f, R.drawable.n2_w3f, R.drawable.n2_w4f, R.drawable.n2_w5f
    };
    //北2东
    public static final int[] CLASSROOM_N2_E = {
            R.drawable.n2_e1f, R.drawable.n2_e2f, R.drawable.n2_e3f, R.drawable.n2_e4f
    };
    //北3西
    public static final int[] CLASSROOM_N3_W = {
            R.drawable.n3_w1f, R.drawable.n3_w2f, R.drawable.n3_w3f, R.drawable.n3_w4f, R.drawable.n3_w5f
    };
    //北3东
    public static final int[] CLASSROOM_N3_E = {
            R.drawable.n3_e1f, R.drawable.n3_e2f, R.drawable.n3_e3f, R.drawable.n3_e4f, R.drawable.n3_e5f
    };
    //北4
    public static final int[] CLASSROOM_N4= {
            R.drawable.n4_1f, R.drawable.n4_2f, R.drawable.n4_3f, R.drawable.n4_4f
    };
    //北5
    public static final int[] CLASSROOM_N5= {
            R.drawable.n5_1f, R.drawable.n5_2f, R.drawable.n5_3f, R.drawable.n5_4f
    };
    //北6
    public static final int[] CLASSROOM_N6= {
            R.drawable.n6
    };

    //北1
    public static final int[] CLASSROOM_S1 = {
            R.drawable.s1_1f, R.drawable.s1_2f, R.drawable.s1_3f, R.drawable.s1_4f
    };
    //北2西
    public static final int[] CLASSROOM_S2_W = {
            R.drawable.s2_w1f, R.drawable.s2_w2f, R.drawable.s2_w3f, R.drawable.s2_w4f, R.drawable.s2_w5f
    };
    //北2东
    public static final int[] CLASSROOM_S2_E = {
            R.drawable.s2_e1f, R.drawable.s2_e2f, R.drawable.s2_e3f, R.drawable.s2_e4f
    };
    //北3西
    public static final int[] CLASSROOM_S3_W = {
            R.drawable.s3_w1f, R.drawable.s3_w2f, R.drawable.s3_w3f, R.drawable.s3_w4f, R.drawable.s3_w5f
    };
    //北3东
    public static final int[] CLASSROOM_S3_E = {
            R.drawable.s3_e1f, R.drawable.s3_e2f, R.drawable.s3_e3f, R.drawable.s3_e4f, R.drawable.s3_e5f
    };
    //北4
    public static final int[] CLASSROOM_S4= {
            R.drawable.s4_1f, R.drawable.s4_2f, R.drawable.s4_3f, R.drawable.s4_4f
    };
    //北5
    public static final int[] CLASSROOM_S5= {
            R.drawable.s5_1f, R.drawable.s5_2f, R.drawable.s5_3f, R.drawable.s5_4f
    };
    //北6
    public static final int[] CLASSROOM_S6= {
            R.drawable.s6
    };
    //中核
    public static final int[] Z = {
            R.drawable.z_1f, R.drawable.z_2f,
            R.drawable.z_3f, R.drawable.z_4f,
            R.drawable.z_5f, R.drawable.z_6f,
            R.drawable.z_7f, R.drawable.z_8f,
    };
}
