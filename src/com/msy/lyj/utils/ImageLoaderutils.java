
package com.msy.lyj.utils;

import java.io.File;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageLoaderutils {
private  static	ImageLoader loader;
private  static  ImageLoaderConfiguration.Builder confbuilder;
private   static ImageLoaderConfiguration conf;
private   static DisplayImageOptions.Builder optbuilder;
private  static DisplayImageOptions opt;
//����ע��ŵ�imageloader����
	public static  ImageLoader getInstance(Context context)
	{
	
		loader=ImageLoader.getInstance();
		confbuilder=new ImageLoaderConfiguration.Builder(context);
		File file=new File("/mnt/sdcard/gather/imageloader");
		//�ƶ�sdcard�����·��
		confbuilder.discCache(new UnlimitedDiscCache(file));
		//�����ͼƬ����
		confbuilder.discCacheFileCount(100);
		//������������
		confbuilder.discCacheSize(1024*1024*10*10);
		conf=confbuilder.build();
		loader.init(conf);
		return loader;
	}
	//������ʾͼƬʹ��opt
	public   static DisplayImageOptions getOpt(int EmptyPicture,int FailPicture)
	{
		optbuilder=new DisplayImageOptions.Builder();
		//uri ����Ĭ��ͼƬ
		optbuilder.showImageForEmptyUri(EmptyPicture);
		//ͼƬ��ȡʧ�� ���ص�Ĭ��ͼƬ
		optbuilder.showImageOnFail(FailPicture);
		optbuilder.cacheInMemory(true);//���ڴ滺��
		optbuilder.cacheOnDisc(true);//��Ӳ�̻���
		opt=optbuilder.build();
		return opt;
	}
	
	//������ʾͼƬʹ��opt  ListView�� �û���ͷ��
		/*public   static DisplayImageOptions getOpt2()
		{
			optbuilder=new DisplayImageOptions.Builder();
			//uri ����Ĭ��ͼƬ
			optbuilder.showImageForEmptyUri(R.drawable.r7);
			//ͼƬ��ȡʧ�� ���ص�Ĭ��ͼƬ
			optbuilder.showImageOnFail(R.drawable.r7);
			optbuilder.cacheInMemory(true);//���ڴ滺��
			optbuilder.cacheOnDisc(true);//��Ӳ�̻���
			opt=optbuilder.build();
			return opt;
		}
		
		//������ʾͼƬʹ��opt   ListView��  ���ͼ��
		public   static DisplayImageOptions getOpt3(int EmptyPicture,int FailPicture)
		{
			optbuilder=new DisplayImageOptions.Builder();
			//uri ����Ĭ��ͼƬ
			optbuilder.showImageForEmptyUri(EmptyPicture);
			//ͼƬ��ȡʧ�� ���ص�Ĭ��ͼƬ
			optbuilder.showImageOnFail(FailPicture);
			optbuilder.cacheInMemory(true);//���ڴ滺��
			optbuilder.cacheOnDisc(true);//��Ӳ�̻���
			opt=optbuilder.build();
			return opt;
		}*/

}
