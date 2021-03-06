package com.yhjiankang.business.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by 马小布 on 2015/6/13.
 */
public class KeyboardLinearLayoutView extends LinearLayout {
    private static final String TAG = KeyboardLinearLayoutView.class.getSimpleName();
    public static final byte KEYBOARD_STATE_SHOW = -3;
    public static final byte KEYBOARD_STATE_HIDE = -2;
    public static final byte KEYBOARD_STATE_INIT = -1;
    private boolean mHasInit;
    private boolean mHasKeybord;
    private int mHeight;
    private onKybdsChangeListener mListener;

    public KeyboardLinearLayoutView(Context context) {
        super(context);
    }

    public KeyboardLinearLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardLinearLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * set keyboard state listener
     */
    public void setOnkbdStateListener(onKybdsChangeListener listener){
        mListener = listener;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!mHasInit) {
            mHasInit = true;
            mHeight = b;
            if (mListener != null) {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_INIT);
            }
        } else {
            mHeight = mHeight < b ? b : mHeight;
        }
        if (mHasInit && mHeight > b) {//软键盘出现
            mHasKeybord = true;
            if (mListener != null) {

                mListener.onKeyBoardStateChange(KEYBOARD_STATE_SHOW);
            }
            Log.w(TAG, "show keyboard.......");
        }
        if (mHasInit && mHasKeybord && mHeight == b) {
            mHasKeybord = false;
            if (mListener != null) {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_HIDE);
            }
            Log.w(TAG, "hide keyboard.......");
        }
    }

    public interface onKybdsChangeListener{
        public void onKeyBoardStateChange(int state);
    }
}
