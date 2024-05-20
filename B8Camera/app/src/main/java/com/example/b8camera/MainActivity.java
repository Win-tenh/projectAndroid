package com.example.b8camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
// thêm vào đây để lấy dữ liệu từ camera
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

public class MainActivity extends AppCompatActivity {

    private ImageView imgView;
    private ImageButton btn_capture;
    private ActivityResultLauncher cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        Bitmap photo = (Bitmap) o.getData().getExtras().get("data");
                        imgView.setImageBitmap(photo);
                    }
                }
            });

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

        imgView = findViewById(R.id.imgView);
        btn_capture = findViewById(R.id.btn_capture);

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(ACTION_IMAGE_CAPTURE);
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 1);
                    return;
                }
                cameraLauncher.launch(cameraIntent);
            }
        });


    }

}