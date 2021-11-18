#import "VideoCache.h"
#import <KTVHTTPCache/KTVHTTPCache.h>

@implementation VideoCache

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
    return [KTVHTTPCache proxyURLWithOriginalURL:[NSURL URLWithString:[source objectForKey:@"url"]]].absoluteString;
}

RCT_EXPORT_METHOD(convertAsync:(NSString *)url
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
  resolve([KTVHTTPCache proxyURLWithOriginalURL:[NSURL URLWithString:url]].absoluteString);
}

@end
