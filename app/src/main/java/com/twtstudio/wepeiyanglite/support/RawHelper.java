package com.twtstudio.wepeiyanglite.support;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jcy on 2016/8/10.
 */

public class RawHelper {
    /**
     * 改用了阿帕奇的Commons-io框架
     * @param context
     * @param resId
     * @return
     */
    public static String readStringRawFile(Context context, int resId) {
        byte[] bytes = null;
        InputStream inputStream = context.getResources().openRawResource(resId);
        try {
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = new String(bytes);
        return s;
    }
}
