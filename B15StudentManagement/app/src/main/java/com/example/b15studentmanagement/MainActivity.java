package com.example.b15studentmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements View.OnTouchListener {

    private EditText edt_id, edt_name, edt_size;
    private DatabaseHelper db;
    private ArrayAdapter<String> studentAdapter;
    private ArrayList<String> studentList;
    private ArrayList<String> filteredList;
    private int position;

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

        // ánh xạ các view trong layout
        edt_id = findViewById(R.id.edt_id);
        edt_name = findViewById(R.id.edt_name);
        edt_size = findViewById(R.id.edt_size);
        Button btn_add = findViewById(R.id.btn_add);
        Button btn_update = findViewById(R.id.btn_update);
        Button btn_delete = findViewById(R.id.btn_delete);
        Button btn_reset = findViewById(R.id.btn_reset);
        ListView lv_class = findViewById(R.id.lv_class);

        // truyền dữ liệu từ db lên listView
        db = new DatabaseHelper(this);
        studentList = db.getAllStudents();
        filteredList = new ArrayList<>(studentList);
        studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredList);
        lv_class.setAdapter(studentAdapter);

        // nhập mã lớp thì hiện những mã lớp trùng trong listview,
        // nếu mã lớp không trùng thì hiện toàn bộ listview
        edt_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (edt_id.isEnabled())
                    filterList(s.toString());
            }
        });

        // nút thêm student
        btn_add.setOnClickListener(v -> {
            String id = edt_id.getText().toString();
            String name = edt_name.getText().toString();
            String size = edt_size.getText().toString();
            if (validate(id, name, size)) {
                // kiểm tra mã lớp
                if (db.checkStudent(id)) {
                    Toast.makeText(this, "Mã lớp đã tồn tại!", Toast.LENGTH_SHORT).show();
                    edt_id.requestFocus();
                    return;
                }
                // thêm student
                Long idStudent = db.addStudent(id, name, size);
                if (idStudent == -1) {
                    Toast.makeText(this, "Thêm lớp thất bại!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(this, "Thêm lớp thành công!", Toast.LENGTH_SHORT).show();
                    studentList.add(id + " - " + name + " - " + size);
                    filteredList.add(id + " - " + name + " - " + size);
                    studentAdapter.notifyDataSetChanged();
                    // reset edit text
                    reset();
                }
            }
        });

        // chọn vào 1 dòng listView
        lv_class.setOnItemClickListener((parent, view, position, id) -> {
            this.position = position;
            String[] student = studentList.get(position).split(" - ");
            edt_id.setEnabled(false);
            edt_id.setText(student[0]);
            edt_name.setText(student[1]);
            edt_size.setText(student[2]);
        });

        // nút sửa student
        btn_update.setOnClickListener(v -> {
            String id = edt_id.getText().toString();
            String name = edt_name.getText().toString();
            String size = edt_size.getText().toString();
            if (validate(id, name, size)) {
                // kiểm tra mã lớp
                if (!db.checkStudent(id)) {
                    Toast.makeText(this, "Mã lớp không tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // sửa student
                int result = db.updateStudent(id, name, size);
                if (result == -1) {
                    Toast.makeText(this, "Sửa lớp thất bại!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "Sửa lớp thành công!", Toast.LENGTH_SHORT).show();
                studentList.set(position, id + " - " + name + " - " + size);
                filteredList.set(position, id + " - " + name + " - " + size);
                studentAdapter.notifyDataSetChanged();
                // reset edit text
                reset();
            }
        });

        // nút xóa student
        btn_delete.setOnClickListener(v -> {
            String id = edt_id.getText().toString();
            if (id.isEmpty()) {
                Toast.makeText(this, "Hãy chọn lớp cần xóa!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!db.checkStudent(id)) {
                Toast.makeText(this, "Mã lớp không tồn tại!", Toast.LENGTH_SHORT).show();
                return;
            }
            // xóa student
            int result = db.deleteStudent(id);
            if (result == -1) {
                Toast.makeText(this, "Xóa lớp thất bại!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Xóa lớp thành công!", Toast.LENGTH_SHORT).show();
            studentList.remove(position);
            filteredList.remove(position);
            studentAdapter.notifyDataSetChanged();
            // reset edit text
            reset();
        });

        // nút reset
        btn_reset.setOnClickListener(v -> reset());

        // hide keyboard
        findViewById(R.id.main).setOnTouchListener(this);
    }

    // validates: sĩ số > 0, các trường không được để trống
    public boolean validate(String id, String name, String size) {
        if (id.isEmpty() || name.isEmpty() || size.isEmpty()) {
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Integer.parseInt(size) <= 0) {
            Toast.makeText(this, "Sĩ số phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
            edt_size.requestFocus();
            return false;
        }
        return true;
    }

    // lọc dữ liệu
    public void filterList(String id) {
        filteredList.clear();
        if (!id.isEmpty()) {
            for (String students : studentList) {
                String[] student = students.split(" - ");
                String idStudent = student[0];
                if (idStudent.toLowerCase().contains(id.toLowerCase())) {
                    filteredList.add(students);
                }
            }
        } else {
            studentAdapter.clear();
            studentAdapter.addAll(studentList);
        }
        studentAdapter.notifyDataSetChanged();
    }

    // làm trống các edit text
    public void reset() {
        edt_id.setEnabled(true);
        edt_id.setText("");
        edt_name.setText("");
        edt_size.setText("");

        edt_id.clearFocus();
        edt_name.clearFocus();
        edt_size.clearFocus();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        edt_id.clearFocus();
        edt_name.clearFocus();
        edt_size.clearFocus();
        return false;
    }
}