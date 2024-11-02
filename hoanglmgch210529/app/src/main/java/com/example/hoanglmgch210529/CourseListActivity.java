package com.example.hoanglmgch210529;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hoanglmgch210529.adapter.CourseAdapter;
import java.util.ArrayList;
import java.util.List;
import com.example.hoanglmgch210529.db.CourseDatabaseHelper;

public class CourseListActivity extends AppCompatActivity {
    private CourseDatabaseHelper dbHelper;
    private RecyclerView rvCourses;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        rvCourses = findViewById(R.id.rvCourses);
        Button btnAddCourse = findViewById(R.id.btnAddCourse);
        dbHelper = new CourseDatabaseHelper(this);

        // Khởi tạo danh sách khóa học
        courseList = dbHelper.getAllCourses();

        // Cấu hình RecyclerView
        rvCourses.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter(this, courseList);
        rvCourses.setAdapter(courseAdapter);

        // Thiết lập sự kiện click cho nút Add Course
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseListActivity.this, AddCourseActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Course newCourse = (Course) data.getSerializableExtra("newCourse");
            if (newCourse != null) {
                dbHelper.addCourse(newCourse);
                courseList.add(newCourse);
                courseAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
            }
        }
    }
}
