package com.example.sqlitebookmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

    private DatabaseHelper dbHelper; // thao tác với database
    private ArrayAdapter<String> adapter; // điều phối dữ liệu với view ListView, trả về ArrayList
    private ArrayList<String> bookList;
    private Intent mainIntent;

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
        ListView listViewBooks = findViewById(R.id.listViewBooks);
        Button btnAddBook = findViewById(R.id.btnAddBook);

        btnAddBook.setOnClickListener(v -> {
            mainIntent = new Intent(this, AddBookActivity.class);
            bookARL.launch(mainIntent);
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

        listViewBooks.setOnItemLongClickListener((parent, view, position, id) -> {
            // Lấy id của sách tại vị trí position
            String bookTitle = bookList.get(position).split(" - ")[0];
            // Hiển thị dialog để xác nhận xóa sách
            new AlertDialog
                    .Builder(MainActivity.this)
                    .setTitle("Delete Book")
                    .setMessage("Are you sure you want to delete this book?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete(DatabaseHelper.TABLE_BOOKS,
                                DatabaseHelper.COLUMN_TITLE + " = ?",
                                new String[]{bookTitle}
                        );
                        db.close();
                        bookList.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNeutralButton("No", null)
                    .show();
            return true;
        });
    }

    private void loadBooks() {
        // xóa dữ liệu cũ
        bookList.clear();
        // truy vấn dữ liệu
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS,
                null, null, null,
                null, null, null);
        // con trỏ bản ghi dl
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COLUMN_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COLUMN_AUTHOR));
                String tags = cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COLUMN_TAGS));
                bookList.add(book(title, author, tags));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    // hàm trả về kiểu String khi add vào ListView
    public String book(String title, String author, String tags) {
        return title + " - " + author + " - " + tags;
    }

    private final ActivityResultLauncher bookARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        mainIntent = o.getData();
                        // thêm dữ liệu vào ArrayList
                        String title = mainIntent.getStringExtra("title");
                        String author = mainIntent.getStringExtra("author");
                        String tags = mainIntent.getStringExtra("tags");
                        String book = book(title, author, tags);
                        bookList.add(book);
                        // cập nhật dữ liệu vào ListView
                        adapter.notifyDataSetChanged();
                    }
                }
            });

}