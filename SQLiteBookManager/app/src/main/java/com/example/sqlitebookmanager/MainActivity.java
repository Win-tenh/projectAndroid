package com.example.sqlitebookmanager;

import static androidx.activity.result.contract.ActivityResultContracts.*;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnAddBook;
    private ListView listViewBooks;
    private DatabaseHelper dbHelper; // thao tác với database
    private ArrayAdapter<String> adapter; // điều phối dữ liệu với view ListView, trả về ArrayList
    private ArrayList<String> bookList;
    private Intent mainIntent;
    private SQLiteDatabase db;
    private Cursor cursor;

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

        // tham chiếu phần tử giao diện
        listViewBooks = findViewById(R.id.listViewBooks);
        btnAddBook = findViewById(R.id.btnAddBook);

        btnAddBook.setOnClickListener(v -> {
            mainIntent = new Intent(this, AddBookActivity.class);
            bookARL.launch(mainIntent);
//            startActivity(mainIntent);
        });

        // tham chiếu đến database
        dbHelper = new DatabaseHelper(this);
        // khởi tạo ArrayList: mảng lưu trữ dữ liệu
        bookList = new ArrayList<>();
        // load dữ liệu từ database
        loadBooks();

        // nhiệm vụ: đưa dữ liệu vào ListView bằng adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                bookList);
        listViewBooks.setAdapter(adapter);
    }

    private void loadBooks() {
        // xóa dữ liệu cũ
        bookList.clear();

        // truy vấn dữ liệu
        db = dbHelper.getReadableDatabase();
        cursor = db.query(DatabaseHelper.TABLE_BOOKS,
                null, null, null,
                null, null, null);

        // con trỏ bản ghi dl
        if (cursor.moveToFirst()) {
            do {
                // getColumnIndexOrThrow: kiểm tra xem cột có tồn tại trong database hay không,
                // trả về -1 nếu không tồn tại
                String title = cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COLUMN_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COLUMN_AUTHOR));
                String tags = cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COLUMN_TAGS));
                bookList.add(title + " - " + author + " - " + tags);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    private ActivityResultLauncher bookARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        mainIntent = o.getData();
                        bookList.add(mainIntent.getStringExtra("book"));
                        adapter.notifyDataSetChanged();
                    }
                }
            });

}