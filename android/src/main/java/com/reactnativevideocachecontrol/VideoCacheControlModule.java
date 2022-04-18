package com.reactnativevideocachecontrol;

import androidx.annotation.NonNull;

import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.headers.HeaderInjector;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;

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
    public Boolean isCached(String url) {
        if (this.proxy == null) {
            this.proxy = new HttpProxyCacheServer(this.reactContext);
        }
        return this.proxy.isCached(url);
    }
}
