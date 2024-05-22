package com.example.sqlitebookmanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddBookActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText editTextTitle, editTextAuthor, editTextTags;
    private Button buttonAddBook, btn_back_main;
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
        btn_back_main = findViewById(R.id.btn_back_main);
        dbHelper = new DatabaseHelper(this);


        // hide keyboard
        findViewById(R.id.main).setOnTouchListener(this);

        Intent returnIntent = getIntent();

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String author = editTextAuthor.getText().toString();
                String tags = editTextTags.getText().toString();

                // Kiểm tra nếu EditText rỗng
                if (title.isEmpty() || author.isEmpty() || tags.isEmpty()) {
                    // Thông báo lỗi
                    Toast.makeText(AddBookActivity.this, "Fields cannot be left blank!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (dbHelper.checkStudent(title)) {
                    Toast.makeText(AddBookActivity.this, "Title already exists ", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_TITLE, title);
                values.put(DatabaseHelper.COLUMN_AUTHOR, author);
                values.put(DatabaseHelper.COLUMN_TAGS, tags);

                long newId = db.insert(DatabaseHelper.TABLE_BOOKS, null, values);
                db.close();

                String newRow = title + " - " + author + " - " + tags;
                returnIntent.putExtra("book", newRow);
                setResult(RESULT_OK, returnIntent);

                // thông báo thành công
                Toast.makeText(AddBookActivity.this, "Book added successfully", Toast.LENGTH_SHORT).show();
                // kết thúc activity
                finish();
            }
        });

        btn_back_main.setOnClickListener(v -> finish());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        editTextTitle.clearFocus();
        editTextAuthor.clearFocus();
        editTextTags.clearFocus();
        return false;
    }
}