package com.joke.template.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.FontMetrics;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.joke.template.R;
import com.joke.template.globalstatic.GlobalStatic;
import com.joke.template.logs.SuperLogs;
import com.joke.template.net.BasicInfomation;
import com.joke.template.utils.FileUtils;
import com.joke.template.utils.ImageTools;

public class CustomGalleryImageAdapter extends BaseAdapter {

	private Context mContext; // ����Context

	private ArrayList<String> mImageIds = null;
	private String descriptions = null;

	
	
//	private int mTextPosx = 0;// x����
//	private int mTextPosy = 0;// y����
//	private int mTextWidth = 0;// ���ƿ��
//	private int mTextHeight = 0;// ���Ƹ߶�
//	private int mFontHeight = 0;// ��������߶�
//	private int mPageLineNum = 0;// ÿһҳ��ʾ������
//	private int mCanvasBGColor = 0;// ������ɫ
//	private int mFontColor = 0;// ������ɫ
//	private int mAlpha = 0;// Alphaֵ
//	private int mRealLine = 0;// �ַ�����ʵ������
//	private int mCurrentLine = 0;// ��ǰ��
//	private int mTextSize = 0;// �����С
//	private String mStrText = "";
//	private Vector<String> mString = null;
//	private Paint mPaint = null;
	
	public CustomGalleryImageAdapter(Context c, Handler oldHandler, ArrayList<String> picUrltList, String discription) { // ����
		descriptions = discription;																			// ImageAdapter
		mContext = c;
		mImageIds = picUrltList;
	}

	public int getCount() { // ��ȡͼƬ�ĸ���
		return mImageIds.size();
	}

	public String getItem(int position) {// ��ȡͼƬ�ڿ��е�λ��
		return mImageIds.get(position);
	}
	
	public String getName(int position) {// ��ȡͼƬ�ڿ��е�λ��
		SuperLogs.info("Filenameff=" + mImageIds.get(position));
		if(mImageIds.get(position).contains("aid")){
			return  FileUtils.getUrlFileName_phpid(mImageIds.get(position));
		}else{
			return  FileUtils.getUrlFileName(mImageIds.get(position));
		}
	
	}

	public long getItemId(int position) {// ��ȡͼƬ�ڿ��е�λ��
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(mContext);
		
		if(position == 0){
			descriptions = descriptions.replace("<br/>", "\n");
			descriptions = descriptions.replace("<br />", "\n");
			
			InputStream is = mContext.getResources().openRawResource(R.drawable.activity_template_background);      
			Bitmap mBitmap = BitmapFactory.decodeStream(is);
			
			Bitmap newb = ImageTools.GetTextIfon(descriptions, mContext, mBitmap);
	        
	        if (newb != null) {
	        	 Drawable drawable = new BitmapDrawable(newb);      
	 	         i.setBackgroundDrawable(drawable);  
			} else {
				i.setImageResource(R.drawable.face28);// ��ImageView������Դ
			}
		}else{
			String imageName;
			if(mImageIds.get(position).contains("aid")){
				imageName = FileUtils.getUrlFileName_phpid(mImageIds.get(position));
			}else{
				imageName = FileUtils.getUrlFileName(mImageIds.get(position));
			}
			
			SuperLogs.info("imageName=" + imageName);
			Bitmap cover = null;
			if (FileUtils
					.isFileExist(GlobalStatic.HEAD_IMG_FOLDER_PATH + imageName)) {
				try {
					cover = BitmapFactory
							.decodeFile(GlobalStatic.HEAD_IMG_FOLDER_PATH
									+ imageName);
				} catch (OutOfMemoryError e) {
					cover = null;
				}
				if(cover != null && cover.getWidth() == -1){
					//���������ͼƬ���Ϊ-1��˵��ͼƬ���󣬽���ɾ��
					FileUtils.deleteFile(GlobalStatic.HEAD_IMG_FOLDER_PATH + imageName);
				}
			}
			if (cover != null) {
				i.setImageBitmap(cover);// ��ImageView������Դ
			} else {
				i.setImageResource(R.drawable.face28);// ��ImageView������Դ
			}
		}
		

		i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));// ���ò���
																// ͼƬ200��200��ʾ
		i.setScaleType(ImageView.ScaleType.FIT_XY);// ���ñ�������

		return i;
	}
	
	
}
