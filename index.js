import { NativeModules } from "react-native";

export default (source) => {
  if (!global.nativeCallSyncHook) {
    return source.url;
  }
  return NativeModules.VideoCache.convert(source);
};

export const convertAsync = NativeModules.VideoCache.convertAsync;
