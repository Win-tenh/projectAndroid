package com.example.sqlitebookmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextAuthor, editTextTags;
    private Button buttonAddBook;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextTags = findViewById(R.id.editTextTags);
        buttonAddBook = findViewById(R.id.buttonAddBook);
        dbHelper = new DatabaseHelper(this);

        Intent idIntent = getIntent();

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String author = editTextAuthor.getText().toString();
                String tags = editTextTags.getText().toString();

                // Kiểm tra nếu EditText rỗng
                if (title.isEmpty() || author.isEmpty() || tags.isEmpty()) {
                    // Thông báo lỗi
                    Toast.makeText(AddBookActivity.this, "Điền vào các ô trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_TITLE, title);
                values.put(DatabaseHelper.COLUMN_AUTHOR, author);
                values.put(DatabaseHelper.COLUMN_TAGS, tags);

                long newId = db.insert(DatabaseHelper.TABLE_BOOKS, null, values);
                db.close();

                // truyền id mới thêm cho ListView
//                idIntent.putExtra("new_id", newId);
//                setResult(RESULT_OK, idIntent);
                // kết thúc activity
                finish();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KeyboardUtil.dispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }
}