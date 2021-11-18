package com.reactnative.videocache;

import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.headers.HeaderInjector;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class VideoCacheModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;
    private HttpProxyCacheServer proxy;

    public VideoCacheModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "VideoCache";
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
        if (source.hasKey("headers")) {
            HeaderInjector injector = new UserAgentHeadersInjector(source.getMap("headers"));
            this.proxy = new HttpProxyCacheServer.Builder(this.reactContext).headerInjector(injector).build();
        } else {
            this.proxy = new HttpProxyCacheServer(this.reactContext);
        }
        promise.resolve(this.proxy.getProxyUrl(source.getString("url")));
    }
}
