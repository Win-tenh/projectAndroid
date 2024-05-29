package com.example.testfirebase.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testfirebase.repository.FirebaseDatabaseHelper;
import com.example.testfirebase.R;
import com.example.testfirebase.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText et_name, et_email;
    private Button btn_add, btn_update, btn_delete;
    private ListView lv_students;
    private List<Student> studentList;
    private FirebaseDatabaseHelper dbHelper;
    private ArrayAdapter<String> studentAdapter;

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
        TextView tvMessage = findViewById(R.id.tv_message);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        lv_students = findViewById(R.id.lv_students);

        // khởi tạo list
        studentList = new ArrayList<>();
        studentAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList);
        lv_students.setAdapter(studentAdapter);
        dbHelper = new FirebaseDatabaseHelper();
        // đọc dữ liệu từ firebase
        loadStudents();

        // thêm sinh viên
        btn_add.setOnClickListener(v -> { addStudent(); });

        // cập nhật sinh viên
        btn_update.setOnClickListener(v -> { updateStudent(); });

        // xóa sinh viên
        btn_delete.setOnClickListener(v -> { deleteStudent(); });

        // click vào item trong listview
        lv_students.setOnItemClickListener((parent, view, position, id) -> {
//            String name = studentList.get(position).getName();
//            String email = studentList.get(position).getEmail();
//            et_name.setText(name);
//            et_email.setText(email);

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
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteStudent() {
        String id = dbHelper.getRef().push().getKey();
        dbHelper.deleteStudent(id);
        et_name.setText("");
        et_email.setText("");
    }

    private void updateStudent() {
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String id = dbHelper.getRef().push().getKey();
        Student student = new Student(id, name, email);
        dbHelper.updateStudent(id,student);
        et_name.setText("");
        et_email.setText("");
    }

    private void addStudent() {
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String id = dbHelper.getRef().push().getKey();
        Student student = new Student(id, name, email);
        dbHelper.addStudent(student);
        et_name.setText("");
        et_email.setText("");
    }

    //        // write database (firebase)
//        String databaseUrl = "https://test-firebase-ecfde-default-rtdb.asia-southeast1.firebasedatabase.app/";
//        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseUrl);
//
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!")
//                .addOnSuccessListener(aVoid -> tvMessage.setText("Success"))
//                .addOnFailureListener(e -> tvMessage.setText("Failed"));
//
//        // read database (firebase)
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String value = snapshot.getValue(String.class);
//                tvMessage.setText(value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("MainActivity", "Failed to read value.", error.toException());
//            }
//        });
}