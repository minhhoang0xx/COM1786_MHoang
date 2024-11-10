package com.example.hoanglmgch210529.Class;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hoanglmgch210529.Model.ClassInstance;
import com.example.hoanglmgch210529.R;
import com.example.hoanglmgch210529.db.CourseDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ClassDetailActivity extends AppCompatActivity {

    private EditText etDate, etTeacher, etComments;
    private Button btnSave;
    private CourseDatabaseHelper dbHelper;
    private int courseId;
    private ClassInstance classInstance; // to hold current class instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        etDate = findViewById(R.id.etDate);
        etTeacher = findViewById(R.id.etTeacher);
        etComments = findViewById(R.id.etComments);
        btnSave = findViewById(R.id.btnSubmit);

        dbHelper = new CourseDatabaseHelper(this);

        // Get courseId from Intent
        courseId = getIntent().getIntExtra("courseId", -1);

        // Load class details if we're editing an existing class
        loadClassDetails();

        // Handle saving updated details
        btnSave.setOnClickListener(v -> {
            if (validateInput()) {
                String date = etDate.getText().toString().trim();
                String teacher = etTeacher.getText().toString().trim();
                String comments = etComments.getText().toString().trim();

                // Format the date in the required format
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = null;
                try {
                    formattedDate = sdf.format(sdf.parse(date));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // If we're editing, update the classInstance
                if (classInstance != null) {
                    classInstance.setDate(formattedDate);
                    classInstance.setTeacher(teacher);
                    classInstance.setComments(comments);

                    // Update class instance in the database
                    dbHelper.updateClassInstance(classInstance);
                } else {
                    // Otherwise, create a new class instance
                    ClassInstance newClassInstance = new ClassInstance(courseId, formattedDate, teacher, comments);
                    dbHelper.addClassInstance(newClassInstance);
                }

                Toast.makeText(this, "Class details saved successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after saving
            }
        });
    }

    private void loadClassDetails() {
        // Get the class instance from the database using courseId
        Cursor cursor = dbHelper.getClassById(courseId);

        if (cursor != null) {
            // Log all available columns
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                System.out.println("Column: " + columnName);
            }

            if (cursor.moveToFirst()) {
                int dateIndex = cursor.getColumnIndex("date");
                int teacherIndex = cursor.getColumnIndex("teacher");
                int commentsIndex = cursor.getColumnIndex("comments");

                // Check if columns exist
                if (dateIndex != -1 && teacherIndex != -1 && commentsIndex != -1) {
                    String date = cursor.getString(dateIndex);
                    String teacher = cursor.getString(teacherIndex);
                    String comments = cursor.getString(commentsIndex);

                    etDate.setText(date);
                    etTeacher.setText(teacher);
                    etComments.setText(comments);
                } else {
                    Toast.makeText(this, "Column not found!", Toast.LENGTH_SHORT).show();
                }
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No class details found.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput() {
        // Validate if required fields are not empty
        if (etDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Date is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etTeacher.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Teacher is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
