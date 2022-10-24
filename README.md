# react-native-video-cache-control

Reference https://github.com/zhigang1992/react-native-video-cache

Boost performance on online video loading and caching

Use following libraries to do the heavy lifting.

- iOS: https://github.com/ChangbaDevs/KTVHTTPCache
- Android: https://github.com/danikula/AndroidVideoCache

## Getting started

`$ yarn add react-native-video-cache-control`

## Install on android

Edit your `android/build.gradle` file and add the following lines:

```
maven {
    url "$rootDir/../node_modules/react-native-video-cache-control/android/libs"
}
```

to your `allprojects/repositories`

## Usage

### Normal

```javascript
// synchronous
import convertToProxyURL from 'react-native-video-cache-control';
...
<Video source={{uri: convertToProxyURL({url: originalURL})}} />
```

```javascript
// async
import { convertAsync } from 'react-native-video-cache-control';
...
const [proxyURL, setProxyURL] = useState();
const getProxyURL = async () => {
    try {
        const url = await convertAsync({url: originalURL});
        setProxyURL(url);
    } catch (error) {
        console.log(error);
    }
}

<Video source={{uri: proxyURL}} />
```

### Use Header

```javascript
import convertToProxyURL from 'react-native-video-cache-control';
...
<Video source={{uri: convertToProxyURL({url: originalURL, headers: {referer: 'https://example.com'}})}} />
```

### Is Cached

```javascript
import { isCached } from 'react-native-video-cache-control';
...

const bool = isCached({
    url: 'https://prod-streaming-video-msn-com.akamaized.net/a8c412fa-f696-4ff2-9c76-e8ed9cdffe0f/604a87fc-e7bc-463e-8d56-cde7e661d690.mp4',
    headers: {
        referer: 'https://example.com',
    },
})
```

### Delete All Cache

```javascript
import { clearCache } from 'react-native-video-cache-control';
...
const clearAllCache = async () => {
    try {
        await clearCache();
    } catch (error) {
        console.log(error);
    }
}
```

### Delete Single Cache

```javascript
import { clearCache } from 'react-native-video-cache-control';
...
const clearSingleCache = async () => {
    try {
        await clearCache('https://prod-streaming-video-msn-com.akamaized.net/a8c412fa-f696-4ff2-9c76-e8ed9cdffe0f/604a87fc-e7bc-463e-8d56-cde7e661d690.mp4');
    } catch (error) {
        console.log(error);
    }
}
```
