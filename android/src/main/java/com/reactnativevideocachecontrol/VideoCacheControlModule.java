package com.reactnativevideocachecontrol;

import android.util.Log;

import androidx.annotation.NonNull;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.headers.HeaderInjector;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;

import java.io.File;

@ReactModule(name = VideoCacheControlModule.NAME)
public class VideoCacheControlModule extends ReactContextBaseJavaModule {
    public static final String NAME = "VideoCacheControl";
    private final ReactApplicationContext reactContext;
    private HttpProxyCacheServer proxy;

    public VideoCacheControlModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String convert(ReadableMap source) {
        Log.d("video", "" + this.proxy);
        if (this.proxy == null) {
            Log.d("video", "headers");
            if (source.hasKey("headers")) {
                HeaderInjector injector = new UserAgentHeadersInjector(source.getMap("headers"));
                Log.d("video",  "" + injector.toString());
                this.proxy = new HttpProxyCacheServer.Builder(this.reactContext).headerInjector(injector).build();
            } else {
                this.proxy = new HttpProxyCacheServer(this.reactContext);
            }
        }
        Log.d("video", "start Listener");
        CacheListener listener = new VideoCacheListener();
        this.proxy.registerCacheListener(listener, source.getString("url"));
        Log.d("video", "end Listener");
        return this.proxy.getProxyUrl(source.getString("url"));
    }

    @ReactMethod
    public void convertAsync(ReadableMap source, Promise promise) {
        if (this.proxy == null) {
            if (source.hasKey("headers")) {
                HeaderInjector injector = new UserAgentHeadersInjector(source.getMap("headers"));
                this.proxy = new HttpProxyCacheServer.Builder(this.reactContext).headerInjector(injector).build();
            } else {
                this.proxy = new HttpProxyCacheServer(this.reactContext);
            }
        }
        promise.resolve(this.proxy.getProxyUrl(source.getString("url")));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public Boolean isCached(ReadableMap source) {
        if (this.proxy == null) {
            if (source.hasKey("headers")) {
                HeaderInjector injector = new UserAgentHeadersInjector(source.getMap("headers"));
                this.proxy = new HttpProxyCacheServer.Builder(this.reactContext).headerInjector(injector).build();
            } else {
                this.proxy = new HttpProxyCacheServer(this.reactContext);
            }
        }
        return this.proxy.isCached(source.getString("url"));
    }
}
