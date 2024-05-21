package com.example.b12advancedcontrol;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView phoneSelect_tv;
    private ListView listPhone_lv;

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

        phoneSelect_tv = findViewById(R.id.phone_select_tv);
        listPhone_lv = findViewById(R.id.list_phone_lv);
        // Khai báo dữ liệu là danh sách tên đt được truyền vào
        final String arr_phoneList[] = {"Iphone 12", "Samsung J7", "XiaoMi 11T Pro", "Sony Extra"};
        // khai báo Adapter và gắn data
        // simple_layout_list_item_1: mỗi content hiển thị trên 1 dòng (ListView hoặc Spinner)
        ArrayAdapter<String> adapter_phoneList =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_phoneList);
        // đưa adapter vào listview
        listPhone_lv.setAdapter(adapter_phoneList);
        // sự kiện click 1 dòng trên List View
        listPhone_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                phoneSelect_tv.setText("Vị trí " + i + ": " + arr_phoneList[i]);
            }
        });
    }
}