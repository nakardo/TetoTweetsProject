package com.example.tetotweets;

/**
 * Created by dacosta on 7/13/13.
 */
public class Log {
    private static final String TAG = "TetoTweetsLog";

    public static void d(String msg) {
        android.util.Log.d(TAG, msg);
    }
}
