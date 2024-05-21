package com.example.b15studentmanagement;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtil {
    private static InputMethodManager inMM;
    public static void hideKeyboard(Activity ac, View view) {
        // view là edit text thì không cần ẩn bàn phím
        if (!(view instanceof EditText)) {
            inMM = (InputMethodManager) ac.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inMM.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public static void dispatchTouchEvent(Activity activity, MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = activity.getCurrentFocus();
            hideKeyboard(activity, v);
        }
    }
}
