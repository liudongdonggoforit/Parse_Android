package com.ss.base.log;

import android.text.TextUtils;
import android.util.Log;

import com.ss.base.BaseApplication;
import com.ss.base.utils.ResourceUtil;

public class LogUtil {
    public static boolean LOG = ResourceUtil.getAppBooleanRes(BaseApplication.getApplication(), "is_debug",
            true);
    public static String TAG_ROOT = "comic";

    public static void json(String json) {
        json(Log.DEBUG, null, json);
    }

    public static void json(int logLevel, String tag, String json) {
        if (LOG) {
            String formatJson = LogFormat.formatBorder(new String[]{LogFormat.formatJson(json)});
            XPrinter.println(logLevel, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatJson);
        }
    }

    public static void xml(String xml) {
        xml(Log.DEBUG, null, xml);
    }


    public static void xml(int logLevel, String tag, String xml) {
        if (LOG) {
            String formatXml = LogFormat.formatBorder(new String[]{LogFormat.formatXml(xml)});
            XPrinter.println(logLevel, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatXml);
        }
    }

    public static void error(Throwable throwable) {
        error(null, throwable);
    }

    public static void error(String tag, Throwable throwable) {
        if (LOG) {
            String formatError = LogFormat.formatBorder(new String[]{LogFormat.formatThrowable(throwable)});
            XPrinter.println(Log.ERROR, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatError);
        }
    }

    private static void msg(int logLevel, String tag, String format, Object... args) {
        if (LOG) {
            String formatMsg = LogFormat.formatBorder(new String[]{LogFormat.formatArgs(format, args)});
            XPrinter.println(logLevel, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatMsg);
        }
    }

    public static void d(String msg, Object... args) {
        msg(Log.DEBUG, null, msg, args);
    }

    public static void d(String tag, String msg, Object... args) {
        msg(Log.DEBUG, tag, msg, args);
    }

    public static void e(String msg, Object... args) {
        msg(Log.ERROR, null, msg, args);
    }

    public static void e(String msg) {
        if (LOG) {
            Log.e(TAG_ROOT, msg);
        }
    }

    public static void e(Object msg) {
        e(msg.toString());
    }

    public static void e(String tag, String msg, Object... args) {
        msg(Log.ERROR, tag, msg, args);
    }
}
