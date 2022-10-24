#import "VideoCacheControl.h"
#import <KTVHTTPCache/KTVHTTPCache.h>

@implementation VideoCacheControl

RCT_EXPORT_MODULE()

RCT_EXPORT_BLOCKING_SYNCHRONOUS_METHOD(convert:(NSDictionary *)source)
{
  if (!KTVHTTPCache.proxyIsRunning) {
    NSError *error;
    [KTVHTTPCache proxyStart:&error];
    if (error) {
      return [source objectForKey:@"url"];
    }
  }
  if ([source objectForKey:@"headers"]) {
    [KTVHTTPCache downloadSetAdditionalHeaders:[source objectForKey:@"headers"]];
  }
  NSURL* videoUrl = [NSURL URLWithString:[source objectForKey:@"url"]];
  @try {
      NSURL *completedCacheFileURL = [KTVHTTPCache cacheCompleteFileURLWithURL:videoUrl];
      if (completedCacheFileURL != nil) {
          return completedCacheFileURL.absoluteString;
      }
  }
  @catch (NSException *exception) {
  }

  return [KTVHTTPCache proxyURLWithOriginalURL:videoUrl].absoluteString;
}

RCT_EXPORT_METHOD(convertAsync:(NSDictionary *)source
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  if (!KTVHTTPCache.proxyIsRunning) {
    NSError *error;
    [KTVHTTPCache proxyStart:&error];
    if (error) {
      reject(@"init.error", @"failed to start proxy server", error);
      return;
    }
  }
  if ([source objectForKey:@"headers"]) {
    [KTVHTTPCache downloadSetAdditionalHeaders:[source objectForKey:@"headers"]];
  }
  NSURL* videoUrl = [NSURL URLWithString:[source objectForKey:@"url"]];
  @try {
      NSURL *completedCacheFileURL = [KTVHTTPCache cacheCompleteFileURLWithURL:videoUrl];
      if (completedCacheFileURL != nil) {
          resolve(completedCacheFileURL.absoluteString);
          return;
      }
  }
  @catch (NSException *exception) {
      reject(@"cacheComplete.error", @"failed to test cacheComplete error", exception);
  }
  resolve([KTVHTTPCache proxyURLWithOriginalURL:videoUrl].absoluteString);
}

RCT_EXPORT_BLOCKING_SYNCHRONOUS_METHOD(isCached:(NSDictionary *)source)
{
  NSURL *completeCacheFileURL= [KTVHTTPCache cacheCompleteFileURLWithURL:[NSURL URLWithString:[source objectForKey:@"url"]]];
  if (completeCacheFileURL == nil) {
    return @NO;
  } else {
    return @YES;
  }
}

RCT_EXPORT_METHOD(clearCache:(NSURL *)url resolver:(RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock) reject)
{
  @try {
      if (url) {
        [KTVHTTPCache cacheDeleteCacheWithURL: url];
        resolve(@YES);
      } else {
        [KTVHTTPCache cacheDeleteAllCaches];
        resolve(@YES);
      }
      
  }
  @catch (NSException *exception) {
    reject(@"delete.error", @"failed to delete cache", exception);
  }
}

@end
