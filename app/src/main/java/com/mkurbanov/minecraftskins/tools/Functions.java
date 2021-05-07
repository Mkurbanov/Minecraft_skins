package com.mkurbanov.minecraftskins.tools;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.mkurbanov.minecraftskins.App;
import com.mkurbanov.minecraftskins.data.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Functions {

    public static void logWrite(String message) {
        Log.i(config.LOG_TAG, message);
    }

    public static void logWriteError(String error_message) {
        Log.e(config.LOG_TAG, error_message);
    }

    @Nullable
    public Bitmap getBitmapFromAssets(String fileName) {
        AssetManager assetManager = App.getInstance().getAssets();

        InputStream istr = null;
        try {
            istr = assetManager.open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(istr);
            istr.close();

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static File getRobotCacheFile(String path) throws IOException {
        File cacheFile = new File(App.getInstance().getCacheDir(), path);
        try {
            InputStream inputStream = App.getInstance().getAssets().open(path);
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            throw new IOException("Could not open png", e);
        }
        return cacheFile;
    }

    public static void copyFile(String filename) {
        AssetManager assetManager = App.getInstance().getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String newFileName = "/data/data/" + App.getInstance().getPackageName() + "/" + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }



    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
