package com.example.hoanglmgch210529.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hoanglmgch210529.Model.Course;

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
                // Kiểm tra chỉ số cột trước khi lấy giá trị
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int dayOfWeekIndex = cursor.getColumnIndex(COLUMN_DAY_OF_WEEK);
                int timeIndex = cursor.getColumnIndex(COLUMN_TIME);
                int capacityIndex = cursor.getColumnIndex(COLUMN_CAPACITY);
                int durationIndex = cursor.getColumnIndex(COLUMN_DURATION);
                int priceIndex = cursor.getColumnIndex(COLUMN_PRICE);
                int classTypeIndex = cursor.getColumnIndex(COLUMN_CLASS_TYPE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);

                // Kiểm tra xem chỉ số cột có hợp lệ không
                if (idIndex != -1 && dayOfWeekIndex != -1 && timeIndex != -1 && capacityIndex != -1
                        && durationIndex != -1 && priceIndex != -1 && classTypeIndex != -1) {

                    // Lấy giá trị của các cột nếu chỉ số hợp lệ
                    int id = cursor.getInt(idIndex);
                    String dayOfWeek = cursor.getString(dayOfWeekIndex);
                    String time = cursor.getString(timeIndex);
                    int capacity = cursor.getInt(capacityIndex);
                    String duration = cursor.getString(durationIndex);
                    double price = cursor.getDouble(priceIndex);
                    String classType = cursor.getString(classTypeIndex);
                    String description = cursor.getString(descriptionIndex); // Có thể là null

                    // Tạo đối tượng Course với dữ liệu từ database
                    Course course = new Course(dayOfWeek, time, capacity, duration, price, classType, description);
                    course.setId(id);

                    // Thêm khóa học vào danh sách
                    courseList.add(course);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return courseList;
    }



    public void deleteCourse(int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Kiểm tra nếu ID hợp lệ
        if (courseId > 0) {
            db.delete(TABLE_COURSES, COLUMN_ID + " = ?", new String[]{String.valueOf(courseId)});
        }
        db.close();
    }

    // Phương thức cập nhật khóa học
    public void updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_OF_WEEK, course.getDayOfWeek());
        values.put(COLUMN_TIME, course.getTime());
        values.put(COLUMN_CAPACITY, course.getCapacity());
        values.put(COLUMN_DURATION, course.getDuration());
        values.put(COLUMN_PRICE, course.getPrice());
        values.put(COLUMN_CLASS_TYPE, course.getClassType());
        values.put(COLUMN_DESCRIPTION, course.getDescription());

        // Cập nhật theo ID
        db.update(TABLE_COURSES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(course.getId())});
        db.close();
    }

    // Phương thức lấy khóa học theo ID

    public Cursor getCourseById(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSES + " WHERE " + COLUMN_ID + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(courseId)});
    }


}
