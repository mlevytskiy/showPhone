package com.github.overotherSample;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by max on 13.03.16.
 */
public class App extends Application {

    private static final String KEY_PHONE = "phone";
    private static final String STORAGE = "storage";

    private static SharedPreferences memory;
    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = this;
        memory = getSharedPreferences(STORAGE, MODE_PRIVATE);
    }

    public static void savePhone(String phone) {
        memory.edit().putString(KEY_PHONE, phone).apply();
    }

    public static String loadPhone() {
        String phone = memory.getString(KEY_PHONE, "");
//        if (TextUtils.isEmpty(phone)) {
//            TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            phone = tMgr.getLine1Number();
//        }
        return phone;
    }

}
