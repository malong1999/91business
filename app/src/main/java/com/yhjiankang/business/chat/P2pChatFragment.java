package com.yhjiankang.business.chat;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhjiankang.business.R;
import com.yhjiankang.business.chat.common.MessageType;
import com.yhjiankang.business.chat.view.audio.MediaManager;
import com.yhjiankang.business.chat.view.displaypanel.RecyclerAdapter;
import com.yhjiankang.business.chat.view.inputpanel.InputPanel;
import com.yhjiankang.business.chat.view.inputpanel.OnActionsPanelItemSeletorListener;
import com.yhjiankang.business.common.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 封装im
 */
public class P2pChatFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    public static final String TAG = "P2pChatFragment";
    private static final int IMAGE_REQUEST_CODE = 0;

    private static final int CAMERA_REQUEST_CODE = 1;

    private static final int RESULT_REQUEST_CODE = 2;

    private static final int MY_CAMMER = 122;
    /**
     * 主界面
     */
    private View rootView;

    /**
     * 聊天对象
     * p2p对方Account或者群id
     */
    protected String sessionId;
    /**
     * 输入面板
     */
    protected InputPanel inputPanel;

    /**
     * 显示面板
     */
    private RecyclerView mRecyclerView;

    /**
     * 消息列表
     */
    private List<IMMessage> mIMMessageList;
    private RecyclerAdapter mRecyclerAdapter;
    private Uri mOutputMediaFileUri;

    public P2pChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_p2p_chat, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.messageListView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mIMMessageList = new ArrayList<>();
        mRecyclerAdapter = new RecyclerAdapter(getActivity(), mIMMessageList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parseIntent();
    }

    /**
     * 解析getArguments()传入参数
     */
    private void parseIntent() {
//        sessionId = getArguments().getString("account");//聊天对象Extras.EXTRA_ACCOUNT

        if (inputPanel == null) {
            inputPanel = new InputPanel(getActivity(), rootView, this);
            inputPanel.setOnActionsPanelItemSeletorListener(new OnActionsPanelItemSeletorListener() {
                @Override
                public void onImageSelect() {
                    Intent intentFromGallery = new Intent();
                    intentFromGallery.setType("image/*"); // 设置文件类型
                    intentFromGallery.setAction(Intent.ACTION_PICK);
                    startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                }

                @Override
                public void onCaptureSelect() {
                    Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mOutputMediaFileUri = getOutputMediaFileUri();
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, mOutputMediaFileUri);
                    startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                    /*} else {
                        EasyPermissions.requestPermissions(this, "需要相机权限",
                                MY_CAMMER, Manifest.permission.CAMERA);
                    }*/
                }
            });
//            inputPanel.setCustomization(customization);
        } else {
//            inputPanel.reload(container, customization);
        }


    }

    public void addMessageListItem(IMMessage imMessage) {
        mIMMessageList.add(imMessage);
        mRecyclerAdapter.notifyItemInserted(mIMMessageList.size());

    }

    /*——————————————————lifecity——————————————*/

    @Override
    public void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if (resultCode == getActivity().RESULT_CANCELED) {
                } else {
                    Uri imageUrl = data.getData();
                    IMMessage imMessage = new IMMessage("lisi", "wangwu", imageUrl, MessageType.RIGHT_IMAGE);
                    addMessageListItem(imMessage);
                    inputPanel.toggleActionPanelLayout();
                }
                break;
            case CAMERA_REQUEST_CODE:
                Log.i(TAG, "onActivityResult: " + resultCode);
                if (resultCode == getActivity().RESULT_CANCELED) {
                } else {
//                    File tempFile = new File(Constants.CACHE_DIR + "temp.jpg");

                    IMMessage imMessage = new IMMessage("lisi", "wangwu", mOutputMediaFileUri, MessageType.RIGHT_IMAGE);
                    addMessageListItem(imMessage);
                    inputPanel.toggleActionPanelLayout();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 保存图片到本地
     *
     * @param bmp
     * @return
     */
    private String saveBmp(Bitmap bmp) {
        String fileName = Constants.CACHE_DIR + System.currentTimeMillis() + ".jpg";
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(fileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.flush();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bmp.recycle();
        return fileName;
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Constants.CACHE_DIR_IMAGE, "chatDir");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.i(TAG, "getOutputMediaFile: " + "目录创建失败");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }

    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }
}
