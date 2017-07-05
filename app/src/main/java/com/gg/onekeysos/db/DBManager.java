package com.gg.onekeysos.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gg.onekeysos.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class DBManager {
    private DBsqliteHelper helper;

    public DBManager(Context context) {
        helper = new DBsqliteHelper(context);
    }


    public void addForOne(People people) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO mypeople VALUES(null,?,?)", new Object[]{people.getName(),people.getNumber()});
        Log.e("DBManager", "添加成功"+people.getName()+",numbei="+people.getNumber());
        db.close();

    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from mypeople");
        Log.e("DBManager", "删除成功");
    }


    public List<People> query() {
        ArrayList<People> peoples = new ArrayList<People>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM mypeople", null);
        while (c.moveToNext()) {
            People people = new People(c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("number")));
            peoples.add(people);
        }
        c.close();
        return peoples;
    }

}
