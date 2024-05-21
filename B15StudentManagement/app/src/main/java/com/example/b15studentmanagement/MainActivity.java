package com.example.b15studentmanagement;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edt_id, edt_name, edt_size;
    private Button btn_add, btn_update, btn_delete, btn_reset;
    private ListView lv_class;
    private DatabaseHelper db;
    private ArrayAdapter<String> studentAdapter;
    private ArrayList<String> studentList;
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
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_reset = findViewById(R.id.btn_reset);
        lv_class = findViewById(R.id.lv_class);

        // truyền dữ liệu từ db lên listView
        db = new DatabaseHelper(this);
        studentList = db.getAllStudents();
        studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        lv_class.setAdapter(studentAdapter);

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
                db.addStudent(id, name, size);
                Toast.makeText(this, "Thêm lớp thành công!", Toast.LENGTH_SHORT).show();
                studentList.add(id + " - " + name + " - " + size);
                studentAdapter.notifyDataSetChanged();
                // reset edit text
                reset();
            }
        });

        // chọn vào 1 dòng listView
        lv_class.setOnItemClickListener((parent, view, position, id) -> {
            this.position = position;
            String[] student = studentList.get(position).split(" - ");
            edt_id.setText(student[0]);
            edt_name.setText(student[1]);
            edt_size.setText(student[2]);
            edt_id.setEnabled(false);
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
                db.updateStudent(id, name, size);
                Toast.makeText(this, "Sửa lớp thành công!", Toast.LENGTH_SHORT).show();
                studentList.set(position, id + " - " + name + " - " + size);
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
            db.deleteStudent(id);
            Toast.makeText(this, "Xóa lớp thành công!", Toast.LENGTH_SHORT).show();
            studentList.remove(position);
            studentAdapter.notifyDataSetChanged();
            // reset edit text
            reset();
        });
        // nút reset
        btn_reset.setOnClickListener(v -> reset());
    }

    // validates: sĩ số > 0, các trường không được để trống
    public boolean validate(String id, String name, String size) {
        if (id.isEmpty() || name.isEmpty() || size.isEmpty()) {
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Integer.parseInt(size) <= 0) {
            Toast.makeText(this, "Sĩ số phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
            edt_size.requestFocus();
            return false;
        }
        return true;
    }

    public void reset() {
        edt_id.setEnabled(true);
        edt_id.setText("");
        edt_name.setText("");
        edt_size.setText("");

    }
    // xử lý sự kiện touch
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KeyboardUtil.dispatchTouchEvent(this, ev);
        edt_id.clearFocus();
        edt_name.clearFocus();
        edt_size.clearFocus();
        return super.dispatchTouchEvent(ev);
    }
}