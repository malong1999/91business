package com.yhjiankang.business.widegt.imagescan;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

//缩略图
public class Thumbnail {
	public static final int DIRECTION_PORTRAIT = 1;
	public static final int DIRECTION_LANDSCAPE = 2;

	private Bitmap mBitmap;

	public Thumbnail(Bitmap bitmap) {
		mBitmap = bitmap.copy(bitmap.getConfig(), true);
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void recycle() {
		if (mBitmap != null) {
			mBitmap.recycle();
			mBitmap = null;
		}
	}

	public Thumbnail scale(int width, int height) {
		return scale(width, height, true);
	}

	public Thumbnail scale(int width, int height, boolean keepAspectRatio) {
		Bitmap thumb = null;
		int bitmapWidth = mBitmap.getWidth();
		int bitmapHeight = mBitmap.getHeight();

		if (width < bitmapWidth || height < bitmapHeight) {
			if (keepAspectRatio) {
				float ratio = 1.f;
				if (width != bitmapWidth) {
					ratio = (float) width / (float) bitmapWidth;
				} else {
					ratio = (float) height / (float) bitmapHeight;
				}
				width *= ratio;
				height *= ratio;
			}
			thumb = Bitmap.createScaledBitmap(mBitmap, width, height, true);
		}
		if (thumb != null) {
			recycle();
			mBitmap = thumb;
		}

		return this;
	}

	public Thumbnail centerCrop(int width, int height) {
		return centerCrop(width, height, Color.TRANSPARENT);
	}

	public Thumbnail centerCrop(int width, int height, int backgroundColor) {
		Bitmap thumb = null;
		Canvas canvas = new Canvas();
		Paint paint = new Paint();
		paint.setDither(false);
		paint.setFilterBitmap(true);
		Rect srcRect = new Rect();
		Rect dstRect = new Rect();

		int canvasWidth, canvasHeight;
		int bitmapWidth = mBitmap.getWidth();
		int bitmapHeight = mBitmap.getHeight();

		canvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, Paint.FILTER_BITMAP_FLAG));
		canvasWidth = width;
		canvasHeight = height;

		if (canvasWidth < bitmapWidth && canvasHeight < bitmapWidth) {
			float ratio = bitmapWidth < bitmapHeight ? (float) width / (float) bitmapWidth : (float) height / (float) bitmapHeight;
			Bitmap bitmap = Bitmap.createScaledBitmap(mBitmap, (int) (bitmapWidth * ratio), (int) (bitmapHeight * ratio), true);
			recycle();
			bitmapWidth = bitmap.getWidth();
			bitmapHeight = bitmap.getHeight();
			mBitmap = bitmap;
		}

		if (bitmapWidth > canvasWidth || bitmapHeight > canvasHeight) {
			Config config = (canvasWidth == width && canvasHeight == height && mBitmap.getConfig() != null) ? mBitmap.getConfig() : Config.ARGB_8888;
			thumb = Bitmap.createBitmap(canvasWidth, canvasHeight, config);
			canvas.setBitmap(thumb);
			canvas.drawColor(backgroundColor);
			if (bitmapWidth > canvasWidth) {
				int left = (bitmapWidth - canvasWidth) / 2;
				srcRect.set(left, 0, left + canvasWidth, bitmapHeight);
				int top = (canvasHeight - bitmapHeight) / 2;
				dstRect.set(0, top, canvasWidth, canvasHeight - top);
			} else {
				int top = (bitmapHeight - canvasHeight) / 2;
				srcRect.set(0, top, bitmapWidth, top + canvasHeight);
				int left = (canvasWidth - bitmapWidth) / 2;
				dstRect.set(left, 0, canvasWidth - left, canvasHeight);
			}
			canvas.drawBitmap(mBitmap, srcRect, dstRect, paint);
		} else {
			Config config = Config.ARGB_8888;
			thumb = Bitmap.createBitmap(canvasWidth, canvasHeight, config);
			canvas.setBitmap(thumb);
			canvas.drawColor(backgroundColor);
			int left = (canvasWidth - bitmapWidth) / 2;
			int top = (canvasHeight - bitmapHeight) / 2;
			canvas.drawBitmap(mBitmap, left, top, paint);
		}
		if (thumb != null) {
			recycle();
			mBitmap = thumb;
		}
		return this;
	}

	public Thumbnail convertToLandscape() {
		return fixDirection(DIRECTION_LANDSCAPE);
	}

	public Thumbnail convertToPortrait() {
		return fixDirection(DIRECTION_PORTRAIT);
	}

	private Thumbnail fixDirection(int direction) {
		boolean shouldRotate = (direction == DIRECTION_LANDSCAPE && mBitmap.getWidth() < mBitmap.getHeight()) || (direction == DIRECTION_PORTRAIT && mBitmap.getHeight() < mBitmap.getWidth());
		Matrix matrix = new Matrix();
		if (shouldRotate) {
			matrix.postRotate(90.f);
		}
		Bitmap rotatedImage = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
		recycle();
		mBitmap = rotatedImage;
		return this;
	}

	public Thumbnail scaleCanvas(int width, int height) {
		return scaleCanvas(width, height, Color.TRANSPARENT);
	}

	public Thumbnail scaleCanvas(int width, int height, int color) {
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(color, PorterDuff.Mode.CLEAR);
		float left = (float) ((width / 2.f) - (mBitmap.getWidth() / 2.f));
		float top = (float) ((height / 2.f) - (mBitmap.getHeight() / 2.f));
		canvas.drawBitmap(mBitmap, left, top, null);
		recycle();
		mBitmap = bitmap;
		return this;
	}

	/*
	 * 按比例缩放
	 */
	public static Bitmap getImage(String srcPath, float ww, float hh) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		// float hh = 800f;// 这里设置高度为800f
		// float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;
	}

	public static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;
	}

	/*
	 * 压缩图片
	 */
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
}