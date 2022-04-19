package com.reactnativevideocachecontrol;

import android.util.Log;

import com.danikula.videocache.CacheListener;

import java.io.File;

public class VideoCacheListener implements CacheListener {
    @Override
    public void onCacheAvailable(File file, String url, int percentsAvailable) {
        Log.d("video", ""+file);
        Log.d("video", ""+percentsAvailable);
    }
}
