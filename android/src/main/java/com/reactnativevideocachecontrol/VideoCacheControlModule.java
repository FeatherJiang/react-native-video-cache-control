package com.reactnativevideocachecontrol;

import android.util.Log;

import androidx.annotation.NonNull;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.Md5FileNameGenerator;
import com.danikula.videocache.headers.HeaderInjector;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@ReactModule(name = VideoCacheControlModule.NAME)
public class VideoCacheControlModule extends ReactContextBaseJavaModule {
    public static final String NAME = "VideoCacheControl";
    private final ReactApplicationContext reactContext;
    private HttpProxyCacheServer proxy;
    private final HashMap<String, CacheListener> listenerHashMap;

    public VideoCacheControlModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.listenerHashMap = new HashMap<>();
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String convert(ReadableMap source) {
        if (this.proxy == null) {
            if (source.hasKey("headers")) {
                HeaderInjector injector = new UserAgentHeadersInjector(source.getMap("headers"));
                this.proxy = new HttpProxyCacheServer.Builder(this.reactContext).headerInjector(injector).build();
            } else {
                this.proxy = new HttpProxyCacheServer(this.reactContext);
            }
        }
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

    @ReactMethod(isBlockingSynchronousMethod = true)
    public void registerCacheListener(String url, Callback callback) {
      if (url == null || callback == null || this.proxy == null) {
        return;
      }
      CacheListener listener = new VideoCacheListener(callback);
      listenerHashMap.put(url, listener);
      this.proxy.registerCacheListener(listener, url);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public void unregisterCacheListener(String url) {
      if (url == null || this.proxy == null) {
        return;
      }
      CacheListener listener = listenerHashMap.get(url);
      this.proxy.unregisterCacheListener(listener);
    }

    @ReactMethod
    public void clearCache(String url,Promise promise) {
      try {
        if (url == null) {
          Utils.cleanVideoCacheDir(this.reactContext);
          promise.resolve(true);
        } else {
          Utils.cleanVideoCacheFile(url, this.reactContext);
          promise.resolve(true);
        }

      } catch (IOException err) {
        Log.w("VideoCacheControl", err);
        promise.reject(err);
      }
    }
}
