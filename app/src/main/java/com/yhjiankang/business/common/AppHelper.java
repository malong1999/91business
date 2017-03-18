package com.yhjiankang.business.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 马小布 on 2015-06-20.
 */

public class AppHelper {

    private static Toast mToast;

    /**
     * 显示消息
     */
    public static void showMessage(Context context, String message) {

        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
