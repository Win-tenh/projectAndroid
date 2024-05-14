package com.example.b4converttemperature;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button cTOf_btn, fTOc_btn, clear_btn;
    private EditText c_ed, f_ed;

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

        c_ed = findViewById(R.id.c_ed);
        f_ed = findViewById(R.id.f_ed);
        cTOf_btn = findViewById(R.id.cTOf_btn);
        fTOc_btn = findViewById(R.id.fTOc_btn);
        clear_btn = findViewById(R.id.clear_btn);

        cTOf_btn.setOnClickListener(this);
        fTOc_btn.setOnClickListener(this);
        clear_btn.setOnClickListener(this);
    }
    DecimalFormat dcf = new DecimalFormat("#.#");

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cTOf_btn) {
            String getC = c_ed.getText()+"";
            getC = getC.replace(',','.');
            double c = Double.parseDouble(getC);
            f_ed.setText(""+dcf.format(c*1.8+32));
        }
        else if (v.getId()==R.id.fTOc_btn) {
            String getF = f_ed.getText()+"";
            getF = getF.replace(",",".");
            double f = Double.parseDouble(getF);
            c_ed.setText(""+dcf.format((f-32)/1.8));
        }
        else if (v.getId()==R.id.clear_btn) {
            c_ed.setText("");
            f_ed.setText("");
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}