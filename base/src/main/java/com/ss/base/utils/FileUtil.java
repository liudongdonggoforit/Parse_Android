package com.ss.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;

import androidx.core.content.FileProvider;

import static android.text.TextUtils.isEmpty;

public class FileUtil {
    // 保存图片到手机指定目录
    public static void savaBitmap(String path, Bitmap bitmap) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileOutputStream fos = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                File imgDir = new File(path);
                if (!imgDir.getParentFile().exists()) {
                    imgDir.getParentFile().mkdirs();
                }
                fos = new FileOutputStream(path);
                if (bitmap != null)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                fos.write(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
        }
    }
    /**
     * 写入文件
     * 断点续传
     *
     * @param in
     * @param file
     */
    public static void writeFile(InputStream in, File file) throws IOException {
        RandomAccessFile savedFile = null;
        long ltest = 0;
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (file != null && file.exists()) {
            ltest = file.length();
        }
        if (in != null) {
            savedFile = new RandomAccessFile(file, "rw");
            savedFile.seek(ltest);
            byte[] buffer = new byte[1024 * 128];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                savedFile.write(buffer, 0, len);
            }
            in.close();
        }
    }

    public static String getFileName(String filePath) {
        if (isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * 读取文件长度
     */
    public static long readFile(File file) {
        if (file != null && file.exists()) {
            return file.length();
        } else {
            return 0;
        }
    }

    public static Uri getFileProvider(Activity activity, File file) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            try {
                Uri uri = FileProvider.getUriForFile(activity, "com.jj.comic.fileprovider", file);
                return uri;
            } catch (IllegalArgumentException e) {
                return Uri.fromFile(file);
            }
        } else {
            return Uri.fromFile(file);
        }
    }
    public static String getCaCheSize(Context context) {
        long l = 0;
        try {
            l += getFolderSize(new File("/data/data/" + context.getPackageName() + "/app_webview"));
            l += getFolderSize(context.getCacheDir());
//                l += getFolderSize(new java.io.File("/data/data/" + (context.getPackageName() + "/databases")));
            l += getFolderSize(new File("/data/data/" + context.getPackageName() + "/shared_prefs/pdr.xml"));
            l += getFolderSize(context.getFilesDir());
            l += (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? getFolderSize(context.getExternalCacheDir()) : 0L);
            l += (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? getFolderSize(context.getExternalFilesDir("")) : 0L);
//            l += (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? getFolderSize(new java.io.File(XDroidConf.DOWNLOAD_PATH)) : 0L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFormatSize(l);
    }
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "T";
    }
    public static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if (item.isDirectory()) {
                    deleteFilesByDirectory(item);
                } else
                    item.delete();
            }
        }
    }
    public static boolean deleteFile(String path) {
        if (isEmpty(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

}
