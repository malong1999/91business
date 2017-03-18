package com.yhjiankang.business.chat.emoji;

public interface IEmoticonSelectedListener {
	/**
	 * 当表情被选择
	 * @param key
     */
	void onEmojiSelected(String key);


	/**
	 * 贴纸被选择
	 * @param categoryName
	 * @param stickerName
     */
	void onStickerSelected(String categoryName, String stickerName);
}
