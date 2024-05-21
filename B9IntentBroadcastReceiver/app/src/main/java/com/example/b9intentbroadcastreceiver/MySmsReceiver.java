package com.example.b9intentbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MySmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        processReceive(intent,context);

        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void processReceive(Intent intent, Context context) {
        Bundle extras = intent.getExtras();
        String msg="", body="", address="";
        if (extras != null) {
            Object[] smsEtra = (Object[])extras.get("pdus");
            for (int i=0;i<smsEtra.length;i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsEtra[i]);
                body = sms.getMessageBody();
                address = sms.getOriginatingAddress();
                msg += "Có 1 tin nhắn từ "+address+"\n"+body+" vừa gọi đến";
            }
            // hiển thị
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        }
    }


}