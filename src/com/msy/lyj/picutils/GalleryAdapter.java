package com.msy.lyj.picutils;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {

	int mGalleryItemBackground;
	private Context mContext;
	//private Integer[] mImageIds;
	private ImageView[] mImages;
	Bitmap[] ivpictures;
	public GalleryAdapter(Context c, Integer[] ImageIds) {
		
		//mImageIds = ImageIds;
		//mImages = new ImageView[mImageIds.length];
	}

	public GalleryAdapter(Context c, Bitmap[] ivpictures) {
		mContext = c;
		this.ivpictures=ivpictures;
		System.out.println(ivpictures+"Îª¿Õ£¿");
		mImages = new ImageView[ivpictures.length];
	}

	public boolean createReflectedImages() {
		final int reflectionGap = 4;
		int index = 0;

		for (int i=0;i<ivpictures.length;i++) {
			Bitmap originalImage = ivpictures[i];
			int width = originalImage.getWidth();
			int height = originalImage.getHeight();
			System.out.println("----------1-------------");
			Matrix matrix = new Matrix();
//			matrix.setRotate(30);
			matrix.preScale(1, -1);
			System.out.println("----------2-------------");
			Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
					height / 2, width, height / 2, matrix, false);

			Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
					(height + height / 2), Config.ARGB_8888);

			Canvas canvas = new Canvas(bitmapWithReflection);
			canvas.drawBitmap(originalImage, 0, 0, null);
			System.out.println("----------3-------------");
			Paint deafaultPaint = new Paint();
			// ï¿½ï¿½ï¿½ï¿½ÝµÄ·ï¿½ï¿½ï¿?
			// deafaultPaint.setAntiAlias(true);
			canvas.drawRect(0, height, width, height + reflectionGap,
					deafaultPaint);

			canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
			System.out.println("----------4-------------");
			Paint paint = new Paint();
			// ï¿½ï¿½ï¿½ï¿½ÝµÄ·ï¿½ï¿½ï¿?
			// paint.setAntiAlias(true);
			LinearGradient shader = new LinearGradient(0,
					originalImage.getHeight(), 0,
					bitmapWithReflection.getHeight() + reflectionGap,
					0x70ffffff, 0x00ffffff, TileMode.CLAMP);

			paint.setShader(shader);

			paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
					+ reflectionGap, paint);
			System.out.println("----------5-------------");
			ImageView imageView = new ImageView(mContext);
			imageView.setImageBitmap(bitmapWithReflection);
			imageView.setLayoutParams(new GalleryView.LayoutParams(180, 240));
			// imageView.setScaleType(ScaleType.MATRIX);
			mImages[index++] = imageView;
		}
		return true;
	}

	private Resources getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCount() {
		return ivpictures.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return mImages[position];
	}

	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}

}
