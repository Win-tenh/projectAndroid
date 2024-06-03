package com.example.b15studentmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 2; //
    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SIZE = "size";
    private SQLiteDatabase db;
    private ArrayList<String> students;

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_STUDENTS + " (" +
                    COLUMN_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_SIZE + " TEXT " +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    // lấy danh sách sinh viên và trả về ArrayList<String>
    public ArrayList<String> getAllStudents() {
        students = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String size = cursor.getString(2);
            students.add(id + " - " + name + " - " + size);
        }
        cursor.close();
        db.close();
        return students;
    }

    // kiểm tra xem sinh viên có tồn tại không
    public boolean checkStudent(String id) {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public long addStudent(String id, String name, String size) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_SIZE, size);
        long result = db.insert(TABLE_STUDENTS, null, cv);
        db.close();
        return result;
    }

    public int updateStudent(String id, String name, String size) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_SIZE, size);
        int result = db.update(TABLE_STUDENTS, cv, COLUMN_ID+"=?", new String[]{id});
        db.close();
        return result;
    }

    public int deleteStudent(String id) {
        db = getWritableDatabase();
        int result = db.delete(TABLE_STUDENTS, COLUMN_ID+ "=?", new String[]{id});
        db.close();
        return result;
    }
}