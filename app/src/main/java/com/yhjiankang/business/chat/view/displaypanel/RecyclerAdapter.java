package com.yhjiankang.business.chat.view.displaypanel;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.chat.IMMessage;
import com.yhjiankang.business.chat.common.MessageType;
import com.yhjiankang.business.chat.emoji.MoonUtil;
import com.yhjiankang.business.chat.view.audio.MediaManager;

import java.util.List;

/**
 * Created by 马小布 on 2015/6/23.
 */
public class RecyclerAdapter extends RecyclerView.Adapter {
    public static final String TAG="RecyclerAdapter";
    private Context mContext;
    private List<IMMessage> mIMMessageList;
    private int mMinItemWidth;
    private int mMaxItemWidth;


    public RecyclerAdapter(Context context,List<IMMessage> imMessageList){
        mContext=context;
        mIMMessageList=imMessageList;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mMaxItemWidth = (int) (outMetrics.widthPixels*0.7f);
        mMinItemWidth = (int) (outMetrics.widthPixels*0.2f);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        if (viewType == MessageType.RIGHT_TEXT) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_receycler_text_right, parent, false);
            return new TextRightViewHolder(view);
        } else if (viewType==MessageType.RIGHT_AUDIO){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_recorder_right, parent, false);
            return new RecorderRightViewHolder(view);
        }else if (viewType==MessageType.RIGHT_IMAGE){
            view=LayoutInflater.from(mContext).inflate(R.layout.item_receycler_image_right,parent,false);
            return new ImageRightViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TextRightViewHolder) {
            TextRightViewHolder textRightViewHolder = (TextRightViewHolder) holder;
            MoonUtil.identifyFaceExpression(App.getInstance(), textRightViewHolder.tvText, mIMMessageList.get(position).getMessageText(), ImageSpan.ALIGN_BOTTOM);
//            textRightViewHolder.tvText.setText(mIMMessageList.get(position).getMessageText());
            textRightViewHolder.ivHeader.setImageResource(R.mipmap.icon);
        }else if (holder instanceof RecorderRightViewHolder){
            final RecorderRightViewHolder recorderRightViewHolder= (RecorderRightViewHolder) holder;
            recorderRightViewHolder.seconds.setText(Math.round(mIMMessageList.get(position).getRecorder().getTime())+"\"");
            ViewGroup.LayoutParams layoutParams = recorderRightViewHolder.length.getLayoutParams();
            layoutParams.width = (int)(mMinItemWidth+(mMaxItemWidth/60f*mIMMessageList.get(position).getRecorder().getTime()));
            recorderRightViewHolder.length.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recorderRightViewHolder.mAnimView.setBackgroundResource(R.drawable.play_anim);
                    AnimationDrawable anim = (AnimationDrawable) recorderRightViewHolder.mAnimView.getBackground();
                    anim.start();

                    //播放声音
                    MediaManager.playSound(mIMMessageList.get(position).getRecorder().getFilePath(), new MediaPlayer.OnCompletionListener(){

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            recorderRightViewHolder.mAnimView.setBackgroundResource(R.mipmap.adj);
                        }
                    });
                }
            });
        }else if (holder instanceof ImageRightViewHolder){
            ImageRightViewHolder imageRightViewHolder= (ImageRightViewHolder) holder;
            imageRightViewHolder.ivHeader.setImageResource(R.mipmap.icon);
            // TODO: 2015/7/5 显示图片
            Log.i(TAG, "onBindViewHolder: "+mIMMessageList.get(position).getImageUri());
//            imageRightViewHolder.ivContent.setImageURI(mIMMessageList.get(position).getImageUri());
            App.getInstance().mImageLoader.displayImage(String.valueOf(mIMMessageList.get(position).getImageUri()),imageRightViewHolder.ivContent);

        }
    }

    @Override
    public int getItemCount() {
        return mIMMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mIMMessageList.get(position).getMessageType() == MessageType.RIGHT_TEXT) {
            return MessageType.RIGHT_TEXT;
        }else if (mIMMessageList.get(position).getMessageType()==MessageType.RIGHT_AUDIO){
            return MessageType.RIGHT_AUDIO;
        }else if (mIMMessageList.get(position).getMessageType()==MessageType.RIGHT_IMAGE){
            return MessageType.RIGHT_IMAGE;
        }
        return super.getItemViewType(position);
//        return mIMMessageList.get(position).getMessageType();

    }

    public class TextRightViewHolder extends RecyclerView.ViewHolder {
        private TextView tvText;
        private ImageView ivHeader;

        public TextRightViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.nim_message_item_text_body);
            ivHeader = (ImageView) itemView.findViewById(R.id.iv_header);
        }
    }

    public class RecorderRightViewHolder extends RecyclerView.ViewHolder {
        private TextView seconds;
        private View length;
        private View mAnimView;

        public RecorderRightViewHolder(View itemView) {
            super(itemView);
            seconds = (TextView) itemView.findViewById(R.id.id_recorder_time);
            length = itemView.findViewById(R.id.id_recorder_length);
            mAnimView=itemView.findViewById(R.id.id_recorder_anim);
        }
    }

    public class ImageRightViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivContent;
        private ImageView ivHeader;

        public ImageRightViewHolder(View itemView) {
            super(itemView);
            ivContent= (ImageView) itemView.findViewById(R.id.nim_message_item_image_body);
            ivHeader= (ImageView) itemView.findViewById(R.id.iv_header);
        }
    }

}
