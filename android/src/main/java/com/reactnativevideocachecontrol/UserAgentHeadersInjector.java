package com.reactnativevideocachecontrol;

import com.danikula.videocache.headers.HeaderInjector;
import com.facebook.react.bridge.ReadableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class UserAgentHeadersInjector implements HeaderInjector {

    private ReadableMap headers;

    public UserAgentHeadersInjector(ReadableMap headers) {
        this.headers = headers;
    }

    @Override
    public Map<String, String> addHeaders(String url) {
        Map<String, String> headers = new HashMap<String, String>();
        Iterator<Map.Entry<String, Object>> iterator = this.headers.getEntryIterator();
        while(iterator.hasNext()) {
            Map.Entry<String, Object> entry=iterator.next();
            headers.put(entry.getKey(),(String) entry.getValue());
        }
        return headers;
    }
}