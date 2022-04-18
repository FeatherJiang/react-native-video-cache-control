import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import convertToCache, {
  convertAsync,
  isCached,
} from 'react-native-video-cache-control';

export default function App() {
  const url =
    'https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-720p.mp4';
  const [asyncVersion, setAsyncVersion] = useState<string>();
  const [Cached, setCached] = useState(false);
  useEffect(() => {
    convertAsync({ url })
      .then((fileUrl: string) => {
        console.log(fileUrl);
        setAsyncVersion(fileUrl);
      })
      .catch((e: any) => {
        console.log(e);
      });
    console.log('123', isCached(url));
  }, []);

  useEffect(() => {
    let id = setInterval(() => {
      const bool = isCached(url);
      console.log(bool);
      setCached(bool);
    }, 1000);
    return () => clearInterval(id);
  });

  return (
    <View style={styles.container}>
      <Text style={styles.welcome}>☆Original URL☆ {Cached}</Text>
      <Text style={styles.instructions}>{url}</Text>
      <Text style={styles.welcome}>☆Proxy URL for Video Component☆</Text>
      <Text style={styles.instructions}>{convertToCache({ url })}</Text>
      <Text style={styles.welcome}>☆Async Proxy URL☆</Text>
      <Text style={styles.instructions}>{asyncVersion}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
    padding: 20,
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
