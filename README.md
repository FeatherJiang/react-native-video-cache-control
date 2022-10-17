# react-native-video-cache-control

Reference https://github.com/zhigang1992/react-native-video-cache

Boost performance on online video loading and caching

Use following libraries to do the heavy lifting.

- iOS: https://github.com/ChangbaDevs/KTVHTTPCache
- Android: https://github.com/danikula/AndroidVideoCache

## Getting started

`$ yarn add react-native-video-cache-control`

## Usage

```javascript
import convertToProxyURL from 'react-native-video-cache-control';
...
<Video source={{uri: convertToProxyURL({url: originalURL, headers: {referer: 'https://example.com'}})}} />
```

### Install on android

Edit your `android/build.gradle` file and add the following lines:

```
maven {
    url "$rootDir/../node_modules/react-native-video-cache-control/android/libs"
}
```

to your `allprojects/repositories`
