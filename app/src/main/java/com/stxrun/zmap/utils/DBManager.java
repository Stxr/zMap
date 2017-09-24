package com.stxrun.zmap.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import com.stxrun.zmap.statics.Static;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by stxr on 17-9-22.
 * 数据库处理类
 * 主要是导入现有的数据库
 */

public class DBManager{
    private SQLiteDatabase database;
    public SQLiteDatabase loadDatabase(Context context) {
        this.database = loadDatabase(context,Static.DATABASE_PATH + "/" + Static.DATABASE_NAME);
        return database;
    }

    /**
     * 导入数据库，如果存在则跳过，否则打开数据库
     * @param dbfile
     * @return
     */
    public SQLiteDatabase loadDatabase(Context context, String dbfile) {
        try {
            if (!(new File(dbfile).exists())) { //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                // 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
                File f = new File(Static.DATABASE_PATH + "/");
                // 如 database 目录不存在，新建该目录
                if (!f.exists()) {
                    f.mkdir();
                }
                InputStream is = context.getAssets().open(Static.DATABASE_NAME);; //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.flush();
                fos.close();
                is.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
            return database;
        } catch (FileNotFoundException e) {
            L.e("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            L.e("IO exception");
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 关闭数据库
     */
    public void closeDatabase() {
        this.database.close();
    }

    /**
     * 根据语句查询数据库
     * @param sql 查询的语句
     * @param selectionArgs 查询语句的占位符，一般为null
     * @return 游标
     */
    public Cursor query( String sql, String[] selectionArgs) {
        Cursor cursor=null;
        if (database != null) {
            cursor = database.rawQuery(sql, selectionArgs);
        }
        return cursor;
    }
}
