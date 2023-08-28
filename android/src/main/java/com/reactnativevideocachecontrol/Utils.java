package com.reactnativevideocachecontrol;

import android.content.Context;
import android.util.Log;

import com.danikula.videocache.file.Md5FileNameGenerator;

import java.io.File;
import java.io.IOException;

public class Utils {
  public static void  cleanVideoCacheFile(String url,Context context) throws IOException {
    File cacheDir = StorageUtils.getIndividualCacheDirectory(context);
    String fileName = new Md5FileNameGenerator().generate(url);
    File file = new File(cacheDir,fileName);
    Log.d("VideoCacheControl",url + "," + file.exists() + "," + file.isFile());
    if (file.exists() && file.isFile()) {
      boolean isDeleted = file.delete();
      if (!isDeleted) {
        throw new IOException(String.format("File %s can't be deleted", file.getAbsolutePath()));
      }
    }
  }
  
  public static void cleanVideoCacheDir(Context context) throws IOException {
    File videoCacheDir = StorageUtils.getIndividualCacheDirectory(context);
    cleanDirectory(videoCacheDir);
  }

  private static void cleanDirectory(File file) throws IOException {
    if (!file.exists()) {
      return;
    }
    File[] contentFiles = file.listFiles();
    if (contentFiles != null) {
      for (File contentFile : contentFiles) {
        delete(contentFile);
      }
    }
  }

  private static void delete(File file) throws IOException {
    if (file.isFile() && file.exists()) {
      deleteOrThrow(file);
    }
    cleanDirectory(file);
    deleteOrThrow(file);
  }

  private static void deleteOrThrow(File file) throws IOException {
    if (file.exists()) {
      boolean isDeleted = file.delete();
      if (!isDeleted) {
        throw new IOException(String.format("File %s can't be deleted", file.getAbsolutePath()));
      }
    }
  }
}
