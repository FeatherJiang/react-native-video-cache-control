import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-video-cache-control' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const VideoCacheControl = NativeModules.VideoCacheControl
  ? NativeModules.VideoCacheControl
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

type sourceType = {
  url: string;
  headers?: object;
};

export default (source: sourceType) => {
  return VideoCacheControl.convert(source);
};

export const convertAsync = (source: sourceType) => {
  return VideoCacheControl.convertAsync(source);
};

export const isCached = (source: sourceType) => {
  return VideoCacheControl.isCached(source);
};
