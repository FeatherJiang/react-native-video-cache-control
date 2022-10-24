package com.reactnativevideocachecontrol;

import android.util.Log;

import com.danikula.videocache.CacheListener;
import com.facebook.react.bridge.Callback;

import java.io.File;

public class VideoCacheListener implements CacheListener {
    private Callback callback;

    VideoCacheListener(Callback callback) {
      this.callback = callback;
    }

    @Override
    public void onCacheAvailable(File file, String url, int percentsAvailable) {
        Log.d("VideoCacheControl", ""+file);
        Log.d("VideoCacheControl", ""+percentsAvailable);
        callback.invoke(file, percentsAvailable);
    }
}
