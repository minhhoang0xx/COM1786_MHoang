package com.example.hoanglmgch210529.Class;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoanglmgch210529.Model.ClassInstance;
import com.example.hoanglmgch210529.R;
import com.example.hoanglmgch210529.adapter.ClassAdapter;
import com.example.hoanglmgch210529.db.CourseDatabaseHelper;

import java.util.List;

public class ClassListActivity extends AppCompatActivity {

    private TextView tvInstance;
    private Button btnAddInstance;
    private RecyclerView rvInstance;
    private CourseDatabaseHelper dbHelper;
    private List<ClassInstance> classInstances;
    private int courseId;
    private ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        // Ánh xạ các thành phần giao diện
        tvInstance = findViewById(R.id.tvInstance);
        btnAddInstance = findViewById(R.id.btnAddInstance);
        rvInstance = findViewById(R.id.rvInstance);

        // Khởi tạo database helper
        dbHelper = new CourseDatabaseHelper(this);

        // Lấy courseId và courseName từ Intent
        courseId = getIntent().getIntExtra("courseId", -1);
        String courseName = getIntent().getStringExtra("courseName");
        tvInstance.setText("Class Instances for " + courseName);

        // Thiết lập RecyclerView
        rvInstance.setLayoutManager(new LinearLayoutManager(this));

        // Tải dữ liệu class instances
        loadClassInstances();

        // Xử lý sự kiện thêm instance mới
        btnAddInstance.setOnClickListener(v -> addClassInstance());
    }

    private void loadClassInstances() {
        classInstances = dbHelper.getClassInstancesForCourse(courseId);
        adapter = new ClassAdapter(this, classInstances);
        rvInstance.setAdapter(adapter);
    }

    private void addClassInstance() {
        Intent intent = new Intent(this, ClassAdapter.class);
        intent.putExtra("courseId", courseId);
        startActivityForResult(intent, 1);
    }

    private void editClassInstance(ClassInstance instance) {
        Intent intent = new Intent(this, ClassAdapter.class);
        intent.putExtra("classInstance", instance);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadClassInstances();
        }
    }
}
