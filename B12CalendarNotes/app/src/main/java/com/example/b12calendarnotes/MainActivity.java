package com.example.b12calendarnotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText et_work, et_hour, et_minute;
    private TextView tv_date;
    private Button btn_add_work;
    private ListView lv_works;
    private ArrayList<String> arrWorks;
    private ArrayAdapter<String> arrayAdapter;

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

        et_work = findViewById(R.id.et_work);
        et_hour = findViewById(R.id.et_hour);
        et_minute = findViewById(R.id.et_minute);
        btn_add_work = findViewById(R.id.btn_add_work);
        lv_works = findViewById(R.id.lv_works);
        tv_date = findViewById(R.id.tv_time_now);

        arrWorks = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrWorks);
        lv_works.setAdapter(arrayAdapter);

        // lấy ngày giờ hệ thống để hiển thị lên text view
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tv_date.setText("Hôm nay: " + simpleDateFormat.format(currentDate));

        btn_add_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_work.getText().toString().isEmpty() ||
                        et_hour.getText().toString().isEmpty() ||
                        et_minute.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Info missing");
                    builder.setMessage("Please enter all information of the work");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    builder.show();
                } else {
                    String str = "+ " + et_work.getText().toString() + " - " +
                            et_hour.getText().toString() + ":" +
                            et_minute.getText().toString();
                    arrWorks.add(str);
                    arrayAdapter.notifyDataSetChanged();
                    et_work.setText("");
                    et_hour.setText("");
                    et_minute.setText("");
                }
            }
        });

    }
}