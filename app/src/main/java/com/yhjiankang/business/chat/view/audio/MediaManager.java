package com.yhjiankang.business.chat.view.audio;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;

public class MediaManager {

	private static MediaPlayer mMediaPlayer;
	
	private static boolean isPause;
	public static void playSound(String filepath,OnCompletionListener onCompletionListener) {
		if(mMediaPlayer == null)
		{
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					mMediaPlayer.reset();
					return false;
				}
			});
		}else
		{
			mMediaPlayer.reset();
		}
		
		try {
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setOnCompletionListener(onCompletionListener);
			mMediaPlayer.setDataSource(filepath);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void pause()
	{
		if(mMediaPlayer != null && mMediaPlayer.isPlaying())
		{
			mMediaPlayer.pause();
			isPause = true;
		}
	}
	
	public static void resume()
	{
		if(mMediaPlayer != null && isPause)
		{
			mMediaPlayer.start();
			isPause = false;
		}
	}
	
	public static void release()
	{
		if(mMediaPlayer != null)
		{
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}
