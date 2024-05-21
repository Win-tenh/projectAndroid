package com.example.b9intentservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    // Khai báo đối tượng mà service quản lý
    MediaPlayer myMedia;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    // Gọi hàm tạo đối tượng service quản lý
    @Override
    public void onCreate() {
        super.onCreate();
        myMedia = MediaPlayer.create(MyService.this, R.raw.tinhban);
        myMedia.setLooping(true); // Lặp lại media liên tục
    }

    // Hàm khởi chạy đối tượng
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (myMedia.isPlaying()) myMedia.pause();
        else myMedia.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myMedia.stop();
    }
}