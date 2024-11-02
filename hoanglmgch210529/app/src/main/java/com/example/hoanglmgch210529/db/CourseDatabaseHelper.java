package com.example.hoanglmgch210529.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hoanglmgch210529.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yoga_courses.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_COURSES = "courses";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CAPACITY = "capacity";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CLASS_TYPE = "class_type";
    public static final String COLUMN_DESCRIPTION = "description";

    public CourseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_COURSES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DAY_OF_WEEK + " TEXT NOT NULL,"
                + COLUMN_TIME + " TEXT NOT NULL,"
                + COLUMN_CAPACITY + " INTEGER NOT NULL,"
                + COLUMN_DURATION + " TEXT NOT NULL,"
                + COLUMN_PRICE + " REAL NOT NULL,"
                + COLUMN_CLASS_TYPE + " TEXT NOT NULL,"
                + COLUMN_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_COURSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        onCreate(db);
    }

    // Thêm khóa học vào cơ sở dữ liệu
    public void addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_OF_WEEK, course.getDayOfWeek());
        values.put(COLUMN_TIME, course.getTime());
        values.put(COLUMN_CAPACITY, course.getCapacity());
        values.put(COLUMN_DURATION, course.getDuration());
        values.put(COLUMN_PRICE, course.getPrice());
        values.put(COLUMN_CLASS_TYPE, course.getClassType());
        values.put(COLUMN_DESCRIPTION, course.getDescription());
        db.insert(TABLE_COURSES, null, values);
        db.close();
    }

    // Lấy danh sách tất cả các khóa học
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                // Kiểm tra giá trị trả về từ getColumnIndex()
                int dayOfWeekIndex = cursor.getColumnIndex(COLUMN_DAY_OF_WEEK);
                int timeIndex = cursor.getColumnIndex(COLUMN_TIME);
                int capacityIndex = cursor.getColumnIndex(COLUMN_CAPACITY);
                int durationIndex = cursor.getColumnIndex(COLUMN_DURATION);
                int priceIndex = cursor.getColumnIndex(COLUMN_PRICE);
                int classTypeIndex = cursor.getColumnIndex(COLUMN_CLASS_TYPE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);

                if (dayOfWeekIndex != -1) {
                    course.setDayOfWeek(cursor.getString(dayOfWeekIndex));
                }
                if (timeIndex != -1) {
                    course.setTime(cursor.getString(timeIndex));
                }
                if (capacityIndex != -1) {
                    course.setCapacity(cursor.getInt(capacityIndex));
                }
                if (durationIndex != -1) {
                    course.setDuration(cursor.getString(durationIndex));
                }
                if (priceIndex != -1) {
                    course.setPrice(cursor.getDouble(priceIndex));
                }
                if (classTypeIndex != -1) {
                    course.setClassType(cursor.getString(classTypeIndex));
                }
                if (descriptionIndex != -1) {
                    course.setDescription(cursor.getString(descriptionIndex));
                }

                courseList.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return courseList;
    }


    // Thêm các phương thức khác như xóa hoặc cập nhật khóa học nếu cần
}
