package com.example.b4bmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Button calBMI_btn;
    private EditText name_ed, height_ed, weight_ed, bmi_ed, dia_ed;
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
        getSupportActionBar().hide();

        calBMI_btn = findViewById(R.id.cal_BMI_btn);
        name_ed = findViewById(R.id.name_ed);
        height_ed = findViewById(R.id.height_ed);
        weight_ed = findViewById(R.id.weight_ed);
        bmi_ed = findViewById(R.id.bmi_ed);
        dia_ed = findViewById(R.id.diagnostic_ed);

        bmi_ed.setEnabled(false);
        dia_ed.setEnabled(false);

        calBMI_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double H = Double.parseDouble(height_ed.getText()+"");
                double W = Double.parseDouble(weight_ed.getText()+"");
                double BMI = W / Math.pow(H,2);
                String Diagnostic = "";
                if (BMI < 18) Diagnostic = "Gầy";
                else if (BMI < 24.9) Diagnostic = "Bình thường";
                else if (BMI < 29.9) Diagnostic = "Béo phì độ I";
                else if (BMI < 34.9) Diagnostic = "Béo phì độ II";
                else Diagnostic = "Béo phì độ III";

                DecimalFormat dcf = new DecimalFormat("#.0");
                bmi_ed.setText(dcf.format(BMI));
                dia_ed.setText(Diagnostic);
            }
        });
    }
}