import { NativeModules } from "react-native";

export default (url, headers) => {
  if (!global.nativeCallSyncHook) {
    return url;
  }
  return NativeModules.VideoCache.convert(url, headers);
};

export const convertAsync = NativeModules.VideoCache.convertAsync;
