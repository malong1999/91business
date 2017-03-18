package com.yhjiankang.business.chat.view.inputpanel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.MaterialSimpleListAdapter;
import com.yhjiankang.business.adapter.MaterialSimpleListItem;
import com.yhjiankang.business.chat.IMMessage;
import com.yhjiankang.business.chat.P2pChatFragment;
import com.yhjiankang.business.chat.common.MessageType;
import com.yhjiankang.business.chat.emoji.EmoticonPickerView;
import com.yhjiankang.business.chat.emoji.IEmoticonSelectedListener;
import com.yhjiankang.business.chat.emoji.MoonUtil;
import com.yhjiankang.business.chat.utils.string.StringUtil;
import com.yhjiankang.business.chat.view.actionspanel.ActionsPanel;
import com.yhjiankang.business.chat.view.audio.AudioRecorderButton;
import com.yhjiankang.business.chat.view.audio.Recorder;


/**
 * Created by 马小布 on 2015/6/20.
 */
public class InputPanel implements IEmoticonSelectedListener {
    private static final String TAG = "InputPanel";

    private static final int SHOW_LAYOUT_DELAY = 200;

    private View mRootView;
    private Handler uiHandler;
    private Activity mActivity;
    private P2pChatFragment mFragment;
    private OnActionsPanelItemSeletorListener mOnActionsPanelItemSeletorListener;

    /**
     * 更多布局
     */
    protected View actionPanelBottomLayout; // 更多布局
    /**
     * frg总部局
     */
    protected LinearLayout messageActivityBottomLayout;
    /**
     * 底部横条总布局
     */
    protected View messageInputBar;
    /**
     * 文本消息选择按钮
     * 左侧切换成文本
     */
    protected View switchToTextButtonInInputBar;// 文本消息选择按钮
    /**
     * 语音消息选择按钮
     * 左侧切换成语音
     */
    protected View switchToAudioButtonInInputBar;// 语音消息选择按钮
    /**
     * 更多消息选择按钮
     * 加号
     */
    protected View moreFuntionButtonInInputBar;// 更多消息选择按钮
    /**
     * 发送消息按钮
     */
    protected View sendMessageButtonInInputBar;// 发送消息按钮
    /**
     * 表情
     */
    protected View emojiButtonInInputBar;// 发送消息按钮

    /**
     * 表情
     * 贴图表情控件
     */
    protected EmoticonPickerView emoticonPickerView;  // 贴图表情控件

    /**
     * 录音按钮
     */
    protected AudioRecorderButton audioRecordBtn; // 录音按钮


    /**
     * 文本消息编辑框
     */
    protected EditText messageEditText;


    /**
     * 切换文本，语音按钮布局
     */
    protected FrameLayout textAudioSwitchLayout;



    /**
     * true显示文本发送状态
     */
    private boolean isTextAudioSwitchShow = true;
    /**
     * true键盘正在显示
     */
    private boolean isKeyboardShowed = true;
    /**
     * true加号面板初始化过
     */
    private boolean actionPanelBottomLayoutHasSetup = false;

    public InputPanel(Activity activity,View rootView,P2pChatFragment fragment){
        mActivity=activity;
        mRootView=rootView;
        mFragment=fragment;

        this.uiHandler = new Handler();
        init();
    }

    private void init() {
        initViews();
        initInputBarListener();
        initTextEdit();
        restoreText(false);
    }

    private void initViews() {
        messageActivityBottomLayout = (LinearLayout) mRootView.findViewById(R.id.messageActivityBottomLayout);
        messageInputBar = mRootView.findViewById(R.id.textMessageLayout);
        switchToTextButtonInInputBar = mRootView.findViewById(R.id.buttonTextMessage);
        switchToAudioButtonInInputBar = mRootView.findViewById(R.id.buttonAudioMessage);
        moreFuntionButtonInInputBar = mRootView.findViewById(R.id.buttonMoreFuntionInText);
        emojiButtonInInputBar = mRootView.findViewById(R.id.emoji_button);
        sendMessageButtonInInputBar = mRootView.findViewById(R.id.buttonSendMessage);
        messageEditText = (EditText) mRootView.findViewById(R.id.editTextMessage);

        // 语音
        audioRecordBtn = (AudioRecorderButton) mRootView.findViewById(R.id.audioRecord);

        // 表情
        emoticonPickerView = (EmoticonPickerView) mRootView.findViewById(R.id.emoticon_picker_view);

        // 显示录音按钮
        switchToTextButtonInInputBar.setVisibility(View.GONE);
        switchToAudioButtonInInputBar.setVisibility(View.VISIBLE);

        // 文本录音按钮切换布局
        textAudioSwitchLayout = (FrameLayout) mRootView.findViewById(R.id.switchLayout);
        if (isTextAudioSwitchShow) {
            textAudioSwitchLayout.setVisibility(View.VISIBLE);
        } else {
            textAudioSwitchLayout.setVisibility(View.GONE);
        }



    }

    /**
     * 设置监听
     */
    private void initInputBarListener() {
        switchToTextButtonInInputBar.setOnClickListener(clickListener);
        switchToAudioButtonInInputBar.setOnClickListener(clickListener);
        emojiButtonInInputBar.setOnClickListener(clickListener);
        sendMessageButtonInInputBar.setOnClickListener(clickListener);
        moreFuntionButtonInInputBar.setOnClickListener(clickListener);
        audioRecordBtn.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filepath) {
                // TODO: 2015/6/20  处理音频按钮抬起发送事件
                Recorder recorder = new Recorder(seconds, filepath);
                IMMessage imMessage = new IMMessage("lisi", "wangwu", recorder, MessageType.RIGHT_AUDIO);
                mFragment.addMessageListItem(imMessage);

            }
        });
    }
    /**
     * 文本切换监听
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v == switchToTextButtonInInputBar) {
                switchToTextLayout(true);// 显示文本发送的布局
            } else if (v == sendMessageButtonInInputBar) {//发送按钮点击事件
                onTextMessageSendButtonPressed();
            } else if (v == switchToAudioButtonInInputBar) {
                switchToAudioLayout();
            } else if (v == moreFuntionButtonInInputBar) {//加号处理
                toggleActionPanelLayout();
            } else if (v == emojiButtonInInputBar) {
                toggleEmojiLayout();
            }
        }
    };

    /**
     * 初始化文本编辑框
     */
    private void initTextEdit() {
        messageEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        messageEditText.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    switchToTextLayout(true);
                }
                return false;
            }
        });

        messageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                messageEditText.setHint("");
                checkSendButtonEnable(messageEditText);
            }
        });

        messageEditText.addTextChangedListener(new TextWatcher() {
            private int start;
            private int count;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                this.count = count;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkSendButtonEnable(messageEditText);
                MoonUtil.replaceEmoticons(mActivity, s, start, count);
                int editEnd = messageEditText.getSelectionEnd();
                messageEditText.removeTextChangedListener(this);
                while (StringUtil.counterChars(s.toString()) > 5000 && editEnd > 0) {
                    s.delete(editEnd - 1, editEnd);
                    editEnd--;
                }
                messageEditText.setSelection(editEnd);
                messageEditText.addTextChangedListener(this);

//                sendTypingCommand(); 发送“正在输入”通知
            }
        });
    }
    /**
     * 清空文本输入框
     * @param clearText
     */
    private void restoreText(boolean clearText) {
        if (clearText) {
            messageEditText.setText("");
        }

        checkSendButtonEnable(messageEditText);
    }


    /**
     * 显示发送或更多
     * 没有字的时候显示加号、有字显示发送
     * @param editText
     */
    private void checkSendButtonEnable(EditText editText) {
        String textMessage = editText.getText().toString();
        if (!TextUtils.isEmpty(StringUtil.removeBlanks(textMessage)) && editText.hasFocus()) {
            moreFuntionButtonInInputBar.setVisibility(View.GONE);
            sendMessageButtonInInputBar.setVisibility(View.VISIBLE);
        } else {
            sendMessageButtonInInputBar.setVisibility(View.GONE);
            moreFuntionButtonInInputBar.setVisibility(View.VISIBLE);
        }
    }


    /**
     *  点击edittext，切换键盘输入的布局
     * @param needShowInput  true显示软键盘
     */
    private void switchToTextLayout(boolean needShowInput) {
        hideEmojiLayout();
        hideActionPanelLayout();

        audioRecordBtn.setVisibility(View.GONE);
        messageEditText.setVisibility(View.VISIBLE);
        switchToTextButtonInInputBar.setVisibility(View.GONE);
        switchToAudioButtonInInputBar.setVisibility(View.VISIBLE);

        messageInputBar.setVisibility(View.VISIBLE);

        if (needShowInput) {
            uiHandler.postDelayed(showTextRunnable, SHOW_LAYOUT_DELAY);
        } else {
            hideInputMethod();
        }
    }
    /**
     * 隐藏表情布局
     */
    private void hideEmojiLayout() {
        uiHandler.removeCallbacks(showEmojiRunnable);
        if (emoticonPickerView != null) {
            emoticonPickerView.setVisibility(View.GONE);
        }
    }
    /**
     * 隐藏更多布局
     */
    private void hideActionPanelLayout() {
        uiHandler.removeCallbacks(showMoreFuncRunnable);
        if (actionPanelBottomLayout != null) {
            actionPanelBottomLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 显示键盘布局
     * @param editTextMessage
     */
    private void showInputMethod(EditText editTextMessage) {
        editTextMessage.requestFocus();
        //如果已经显示,则继续操作时不需要把光标定位到最后
        if (!isKeyboardShowed) {
            editTextMessage.setSelection(editTextMessage.getText().length());
            isKeyboardShowed = true;
        }

        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextMessage, 0);

        // TODO: 2015/6/20 消息输入区展开时候的处理
//        mActivity.proxy.onInputPanelExpand();
    }

    /**
     * 隐藏键盘布局
     */
    private void hideInputMethod() {
        isKeyboardShowed = false;
        uiHandler.removeCallbacks(showTextRunnable);
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(messageEditText.getWindowToken(), 0);
        messageEditText.clearFocus();
    }

    /**
     * 发送文本消息  按钮被点击的处理
     */
    private void onTextMessageSendButtonPressed() {
        String text = messageEditText.getText().toString();

        // TODO: 2015/6/20 发送text
        IMMessage imMessage = new IMMessage("lisi", "wangwu", text, MessageType.RIGHT_TEXT);
        mFragment.addMessageListItem(imMessage);
        messageEditText.setText("");


    }

    /**
     * 切换成音频，收起键盘，按钮切换成键盘
     */
    private void switchToAudioLayout() {
        messageEditText.setVisibility(View.GONE);
        audioRecordBtn.setVisibility(View.VISIBLE);
        hideInputMethod();
        hideEmojiLayout();
        hideActionPanelLayout();

        switchToAudioButtonInInputBar.setVisibility(View.GONE);
        switchToTextButtonInInputBar.setVisibility(View.VISIBLE);
    }

    /**
     * 点击“+”号按钮，切换更多布局和键盘
     */
    public void toggleActionPanelLayout() {
        if (actionPanelBottomLayout == null || actionPanelBottomLayout.getVisibility() == View.GONE) {
            showActionPanelLayout();
        } else {
            hideActionPanelLayout();
        }
    }

    /**
     * 显示更多布局
     */
    private void showActionPanelLayout() {
        addActionPanelLayout();
        hideEmojiLayout();
        hideInputMethod();

        uiHandler.postDelayed(showMoreFuncRunnable, SHOW_LAYOUT_DELAY);
        // TODO: 2015/6/20 消息输入区展开时候的处理
//        container.proxy.onInputPanelExpand();
    }

    /**
     * 初始化更多布局
     */
    private void addActionPanelLayout() {
        if (actionPanelBottomLayout == null) {
            View.inflate(mActivity, R.layout.nim_message_activity_actions_layout, messageActivityBottomLayout);
            actionPanelBottomLayout = mRootView.findViewById(R.id.actionsLayout);
            actionPanelBottomLayoutHasSetup = false;
        }
        initActionPanelLayout();
    }

    /**
     * 初始化具体more layout中的项目
     */
    private void initActionPanelLayout() {
        if (actionPanelBottomLayoutHasSetup) {
            return;
        }

        ActionsPanel.init(mRootView,mOnActionsPanelItemSeletorListener);
        actionPanelBottomLayoutHasSetup = true;
    }

    /**
     * 点击表情，切换到表情布局
     * 表情布局的打开关闭
     */
    private void toggleEmojiLayout() {
        if (emoticonPickerView == null || emoticonPickerView.getVisibility() == View.GONE) {
            showEmojiLayout();
        } else {
            hideEmojiLayout();
        }
    }

    /**
     * 显示表情布局
     */
    private void showEmojiLayout() {
        hideInputMethod();
        hideActionPanelLayout();
        hideAudioLayout();

        messageEditText.requestFocus();
        uiHandler.postDelayed(showEmojiRunnable, 200);
        emoticonPickerView.setVisibility(View.VISIBLE);
        emoticonPickerView.show(this);
    }

    @Override
    public void onEmojiSelected(String key) {
        // TODO: 2015/6/20 当表情被选择
        Log.i(TAG, "onEmojiSelected: "+key);
        Editable mEditable = messageEditText.getText();
        if (key.equals("/DEL")) {
            messageEditText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        } else {
            int start = messageEditText.getSelectionStart();
            int end = messageEditText.getSelectionEnd();
            start = (start < 0 ? 0 : start);
            end = (start < 0 ? 0 : end);
            mEditable.replace(start, end, key);
        }
    }

    @Override
    public void onStickerSelected(String categoryName, String stickerName) {
        // TODO: 2015/6/20 当贴纸被选择

    }


    /**
     * 隐藏语音布局
     */
    private void hideAudioLayout() {
        audioRecordBtn.setVisibility(View.GONE);
        messageEditText.setVisibility(View.VISIBLE);
        switchToTextButtonInInputBar.setVisibility(View.VISIBLE);
        switchToAudioButtonInInputBar.setVisibility(View.GONE);
    }

    /*——————————————————————————————————Runnable BEGIN——————————————————————————————————————————————*/
    private Runnable showMoreFuncRunnable = new Runnable() {
        @Override
        public void run() {
            actionPanelBottomLayout.setVisibility(View.VISIBLE);
        }
    };

    /**
     * 显示表情
     */
    private Runnable showEmojiRunnable = new Runnable() {
        @Override
        public void run() {
            emoticonPickerView.setVisibility(View.VISIBLE);
        }
    };

    /**
     * 显示键盘
     */
    private Runnable showTextRunnable = new Runnable() {
        @Override
        public void run() {
            showInputMethod(messageEditText);
        }
    };





    /*——————————————————————————————————Runnable END——————————————————————————————————————————————*/

    public void setOnActionsPanelItemSeletorListener(OnActionsPanelItemSeletorListener listener){
        mOnActionsPanelItemSeletorListener=listener;
    }
}
