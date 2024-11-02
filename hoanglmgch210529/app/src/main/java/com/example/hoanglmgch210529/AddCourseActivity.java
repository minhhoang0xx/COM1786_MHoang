package com.example.hoanglmgch210529;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddCourseActivity extends AppCompatActivity {

    private EditText etDayOfWeek, etTime, etCapacity, etDuration, etPrice, etClassType, etDescription;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        etDayOfWeek = findViewById(R.id.etDayOfWeek);
        etTime = findViewById(R.id.etTime);
        etCapacity = findViewById(R.id.etCapacity);
        etDuration = findViewById(R.id.etDuration);
        etPrice = findViewById(R.id.etPrice);
        etClassType = findViewById(R.id.etClassType);
        etDescription = findViewById(R.id.etDescription);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            // Xử lý nhập liệu
            String dayOfWeek = etDayOfWeek.getText().toString();
            String time = etTime.getText().toString();
            String capacity = etCapacity.getText().toString();
            String duration = etDuration.getText().toString();
            String price = etPrice.getText().toString();
            String classType = etClassType.getText().toString();
            String description = etDescription.getText().toString();

            // Kiểm tra các trường bắt buộc
            if (dayOfWeek.isEmpty() || time.isEmpty() || capacity.isEmpty() ||
                    duration.isEmpty() || price.isEmpty() || classType.isEmpty()) {
                Toast.makeText(AddCourseActivity.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Nếu nhập hợp lệ, hiển thị thông tin khóa học
            String message = "Course Details:\n" +
                    "Day of Week: " + dayOfWeek + "\n" +
                    "Time: " + time + "\n" +
                    "Capacity: " + capacity + "\n" +
                    "Duration: " + duration + "\n" +
                    "Price: " + price + "\n" +
                    "Class Type: " + classType + "\n" +
                    "Description: " + description;

            Toast.makeText(AddCourseActivity.this, message, Toast.LENGTH_LONG).show();

            // Xử lý lưu vào database ở đây (nếu cần)
        });
    }
}
