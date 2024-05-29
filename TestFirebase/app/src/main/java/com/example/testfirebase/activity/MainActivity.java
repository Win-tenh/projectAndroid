package com.example.testfirebase.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testfirebase.adapter.StudentAdapter;
import com.example.testfirebase.repository.FirebaseDatabaseHelper;
import com.example.testfirebase.R;
import com.example.testfirebase.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText et_name, et_email;
    private Button btn_add, btn_update, btn_delete;
    private ListView lv_students;
    private ArrayList<Student> studentList;
    private FirebaseDatabaseHelper dbHelper;
    private StudentAdapter studentAdapter;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // tham chiếu đến các view trong layout
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        lv_students = findViewById(R.id.lv_students);

        // khởi tạo list
        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter(this, studentList);
        lv_students.setAdapter(studentAdapter);
        dbHelper = new FirebaseDatabaseHelper();

        // đọc dữ liệu từ firebase
        loadStudents();

        // thêm sinh viên
        btn_add.setOnClickListener(v -> addStudent());

        // cập nhật sinh viên
        btn_update.setOnClickListener(v -> updateStudent());

        // xóa sinh viên
        btn_delete.setOnClickListener(v -> deleteStudent());

        // click vào item trong listview
        lv_students.setOnItemClickListener((parent, view, position, id) -> {
            Student student = studentList.get(position);
            et_name.setText(student.getName());
            et_email.setText(student.getEmail());
            this.position = position;
        });
    }

    private void loadStudents() {
        dbHelper.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    // lấy dữ liệu từ firebase
                    String id = childSnapshot.getKey();
                    String name = childSnapshot.child("name").getValue(String.class);
                    String email = childSnapshot.child("email").getValue(String.class);
                    Student student = new Student(id, name, email);
                    studentList.add(student);
                }
                // đảo ngược thứ tự student trong studentList
                Collections.reverse(studentList);
                // cập nhật dữ liệu vào adapter
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,
                        "Failed to read value.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // hide keyboard
        findViewById(R.id.main).setOnTouchListener(this);
    }

    private void deleteStudent() {
        if (validatePosition()) return;
        else if (!validateExists()) {
            Toast.makeText(this,
                    "Student is not exists",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // lấy id trong studentList
        String id = studentList.get(position).getId();
        dbHelper.deleteStudent(id);
        // clear dữ liệu trong form
        clearActivity("Student is deleted");
    }

    private void updateStudent() {
        if (validatePosition()) return;
        else if (validate()) return;

        String id = studentList.get(position).getId();
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        Student student = new Student(id, name, email);
        dbHelper.updateStudent(id, student);

        clearActivity("Student is updated");
    }

    private void addStudent() {
        if (validate()) return;
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String id = dbHelper.getRef().push().getKey();
        Student student = new Student(id, name, email);
        dbHelper.addStudent(student);
        // clear dữ liệu trong form
        clearActivity("Student is added");
    }

    // validate dữ liệu của form
    private boolean validate() {
        // kiểm tra et_name có trống không
        if (et_name.getText().toString().isEmpty()) {
            et_name.setError("Name is required");
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
            return true;
        }
        // kiểm tra et_email có trống không
        if (et_email.getText().toString().isEmpty()) {
            et_email.setError("Email is required");
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return true;
        }
        // kiểm tra et_email có đúng định dạng không
        if (!android.util.Patterns.EMAIL_ADDRESS.
                matcher(et_email.getText().toString()).matches()) {
            et_email.setError("Email is invalid");
            Toast.makeText(this, "Email is invalid", Toast.LENGTH_SHORT).show();
            return true;
        }
        // kiểm tra email có bị trùng trong studentList không
        for (Student student : studentList) {
            if (student.getEmail().
                    equals(et_email.getText().toString().trim())) {
                et_email.setError("Email is already exists");
                Toast.makeText(this, "Email is already exists", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public boolean validatePosition() {
        if (position == -1) {
            Toast.makeText(this,
                    "Please select a student",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    // kiểm tra dữ liệu từ name và email có trong studentList không
    public boolean validateExists() {
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();

        for (Student student : studentList)
            if (student.getName().equals(name) &&
                    student.getEmail().equals(email)) {
                return true;
            }
        return false;
    }

    // clear dữ liệu trong form
    public void clearActivity(String toast) {
        et_name.setText("");
        et_email.setText("");
        et_name.clearFocus();
        et_email.clearFocus();
        position = -1;
        if (toast != null) {
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        et_name.clearFocus();
        et_email.clearFocus();
        return false;
    }
}