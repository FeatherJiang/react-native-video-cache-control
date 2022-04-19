import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import convertToProxyURL, { isCached } from 'react-native-video-cache-control';
import VideoPlayer from 'react-native-video-player';

const styles = StyleSheet.create({});

const array = [
  'https://wmstatic.wangli-tech.com/video/course1_1.mp4',
  'https://wmstatic.wangli-tech.com/video/course1_2.mp4',
  'https://wmstatic.wangli-tech.com/video/course1_3.mp4',
  'https://wmstatic.wangli-tech.com/video/course1_4.mp4',
  'https://wmstatic.wangli-tech.com/video/course2_1.mp4',
  'https://wmstatic.wangli-tech.com/video/course2_2.mp4',
  'https://wmstatic.wangli-tech.com/video/course2_3.mp4',
  'https://wmstatic.wangli-tech.com/video/course3_1.mp4',
  'https://wmstatic.wangli-tech.com/video/course3_2.mp4',
  'https://wmstatic.wangli-tech.com/video/course3_3.mp4',
  'https://wmstatic.wangli-tech.com/video/course4_1.mp4',
  'https://wmstatic.wangli-tech.com/video/course4_2.mp4',
];

function App() {
  const [proxyUrl, setProxyUrl] = useState('');
  const [cached, setCached] = useState(false);

  useEffect(() => {
    const bool = isCached({
      url: array[0],
      headers: {
        referer: 'https://wm.wangli-tech.com',
      },
    });
    setCached(bool);
    const url = convertToProxyURL({
      url: array[0],
      headers: {
        referer: 'https://wm.wangli-tech.com',
      },
    });
    setProxyUrl(url);
  }, []);

  return (
    <View style={{ flex: 1 }}>
      <View style={[{ width: '100%', height: 300 }]}>
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
                referer: 'https://wm.wangli-tech.com',
              },
            });
            setCached(bool);
          }}
        >
          <Text>{`url: ${array[0]}`}</Text>
          <Text>{`proxy: ${proxyUrl}`}</Text>
          <Text>{`cached: ${cached}`}</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

export default App;
