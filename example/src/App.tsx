import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import convertToProxyURL, {
  clearCache,
  isCached,
} from 'react-native-video-cache-control';
import VideoPlayer from 'react-native-video-player';

const styles = StyleSheet.create({});

const array = [
  'https://prod-streaming-video-msn-com.akamaized.net/a8c412fa-f696-4ff2-9c76-e8ed9cdffe0f/604a87fc-e7bc-463e-8d56-cde7e661d690.mp4',
];

function App() {
  const [proxyUrl, setProxyUrl] = useState('');
  const [cached, setCached] = useState(false);

  useEffect(() => {
    const bool = isCached({
      url: array[0],
      headers: {
        referer: 'https://www.test.com',
      },
    });
    setCached(bool);
    const url = convertToProxyURL({
      url: array[0],
      headers: {
        referer: 'https://www.test.com',
      },
    });
    setProxyUrl(url);
  }, []);

  return (
    <View style={{ flex: 1 }}>
      <View style={[{ width: 300, height: 300 }]}>
        <VideoPlayer
          video={{
            uri: proxyUrl,
          }}
          videoWidth={300}
          videoHeight={300}
          thumbnail={{ uri: 'https://i.picsum.photos/id/866/1600/900.jpg' }}
        />
      </View>
      <View
        style={[{ flex: 1, justifyContent: 'center', alignItems: 'center' }]}
      >
        <TouchableOpacity
          onPress={() => {
            const bool = isCached({
              url: array[0],
              headers: {
                referer: 'https://www.test.com',
              },
            });
            setCached(bool);
          }}
        >
          <Text>{`url: ${array[0]}`}</Text>
          <Text>{`proxy: ${proxyUrl}`}</Text>
          <Text>{`cached: ${cached}`}</Text>
        </TouchableOpacity>
        <TouchableOpacity
          onPress={async () => {
            try {
              await clearCache(
                'https://prod-streaming-video-msn-com.akamaized.net/a8c412fa-f696-4ff2-9c76-e8ed9cdffe0f/604a87fc-e7bc-463e-8d56-cde7e661d690.mp4'
              );
            } catch (error) {
              console.log(error);
            }
          }}
        >
          <Text>清除缓存</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

export default App;
