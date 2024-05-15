package com.example.b6registerpersonalinformation;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.graphics.drawable.*;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.CompoundButtonCompat;

public class MainActivity extends AppCompatActivity {

    private EditText name_et, cmnd_et, infor_et;
    private RadioGroup degree_gr;
    private CheckBox news_ckb, book_ckb, coding_ckb;
    private Button send_btn;

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

        name_et = findViewById(R.id.name_et);
        cmnd_et = findViewById(R.id.cmnd_et);
        infor_et = findViewById(R.id.infor_et);
        send_btn = findViewById(R.id.btnSend);
        news_ckb = findViewById(R.id.news_ckb);
        book_ckb = findViewById(R.id.book_ckb);
        coding_ckb = findViewById(R.id.coding_ckb);
        degree_gr = findViewById(R.id.degree_btn);

        exitPress();

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformation();
            }
        });
    }
    public void exitPress(MainActivity this) {
        this.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.exit_title));
                builder.setMessage(getString(R.string.exit_message));
                builder.setIcon(R.drawable.exit_icon); // chèn icon
                // nút Yes hiện phải dưới dialog
                builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // đóng activity
                    }
                });
                // nút No hiện trái dưới dialog
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // đóng dialog
                    }
                });
                builder.create().show();
            }
        });
    }
    public void showInformation() {
        if(!validate()) return;
        // lấy name
        String name = name_et.getText().toString().trim()+"\n";
        // lấy cmnd
        String cmnd = cmnd_et.getText().toString().trim()+"\n";
        // lấy bằng cấp
        RadioButton degree_id = findViewById(degree_gr.getCheckedRadioButtonId());
        String degree_rbtn = degree_id.getText()+"\n";
        // lấy sở thích
        String interest="";
        if (news_ckb.isChecked()) interest += news_ckb.getText()+"\n";
        if (book_ckb.isChecked()) interest += book_ckb.getText()+"\n";
        if (coding_ckb.isChecked()) interest += coding_ckb.getText()+"\n";
        // lấy thông tin bổ sung
        String addition = infor_et.getText().toString()+"\n";

        // tạo dialog
        AlertDialog.Builder buider = new AlertDialog.Builder(this);
        buider.setTitle("Thông tin cá nhân");
        buider.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // tạo nội dung
        String message = name
                        + cmnd
                        + degree_rbtn
                        + interest
                        + "----------\n"
                        + "Thông tin bổ sung:\n"
                        + addition
                        + "----------\n";
        buider.setMessage(message); // thiết lập nội dung cho dialog
        buider.create().show(); // hiển thị dialog
    }
    public boolean validate() {
        if (name_et.getText().toString().trim().length() < 3) {
            name_et.requestFocus(); // đặt focus vào edittext
            name_et.selectAll(); // chọn toàn bộ text trong edittext
            // hiện thông báo trên activity này khoảng 3.5s (LENGTH_LONG)
            Toast.makeText(this, "Tên phải dài hơn 3 ký tự!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (cmnd_et.getText().toString().trim().length() != 12) {
            cmnd_et.requestFocus(); // đặt focus vào edittext
            cmnd_et.selectAll(); // chọn toàn bộ text trong edittext
            // hiện thông báo trên activity này khoảng 3.5s (LENGTH_LONG)
            Toast.makeText(this, "CMND chỉ dài 12 ký tự!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (degree_gr.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Phải chọn bằng cấp!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}


