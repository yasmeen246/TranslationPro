package com.example.jasmn.translationpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class databaseHelper  {

    private  SQLiteDatabase db;
    private  MyDatabase database;
    public databaseHelper(Context context) {
        database=new MyDatabase(context);
            db=database.getWritableDatabase();
            }

public boolean insert(String word, String lang1, String word2, String lang2){
        ContentValues values=new ContentValues();
    values.put("word",word);
    values.put("lang1",lang1);
    values.put("translate",word2);
    values.put("lang2",lang2);
    long langues = db.insert("langues", null, values);
    if (langues>0)
        return true;

    return false;
}
public ArrayList<String> get_translat(String word){
        String qury="select translate,lang2 from langues where word like ?";
        String []where={word};
    Cursor cursor = db.rawQuery(qury, where);
    ArrayList<String>trans=new ArrayList<>();
    if (cursor.getCount()>0){
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            trans.add(cursor.getString(i));

        }
        return trans;
    }
    return null;
}

}
