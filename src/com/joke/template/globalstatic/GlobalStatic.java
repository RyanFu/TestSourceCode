package com.joke.template.globalstatic;

import android.os.Environment;

public class GlobalStatic {
	/**
	 * ���߳������taskId
	 * �ڵ���superActivity�е�Asynctaskʱʹ��
	 */
	
	public static final int SEND_TASK = 0x000000101;//����
	public static final int DOWNLOAD_TASK = 0x000000102;//����
	public static final int REQUEST_TASK = 0x000000103;


	public static final String SDCORD_PATH = Environment.getExternalStorageDirectory() + "";
	public static final String MY_FOLDER_PATH = SDCORD_PATH + "/joke/";
	public static final String HEAD_IMG_FOLDER_PATH = SDCORD_PATH + "/joke/hdimg/";
	
	//
	public static final int type_xiaohua = 0x00001000;
	public static final int type_yingwei = 0x00001001;
	public static final int type_yulu = 0x00001002;
	
	//update data task
	public static final int TASK_UPDATE_DATA_SHOUYE = 0x00010000;
	public static final int TASK_MORE_DATA_SHOUYE = 0x00010001;
	
	
	//the count of each time to pull down the data
	public static final int DOWNLOAD_DATA_COUNT_EACH_TIME = 20;
	
	public static String m_url_base = "http://www.59travel.cn/joke/joke_json_show.php?";
	
	public static final int TASK_douniyixiao = 0x00000001;//����һЦ
	public static final int TASK_yulebagua = 0x00000002;//���ְ���
	public static final int TASK_wangluojingdian = 0x00000003;//���羭��
	public static final int TASK_duanxinjingdian= 0x00000004;//���ž���
	public static final int TASK_aiqinghuayu = 0x00000005;//���黰��
	public static final int TASK_shenghuoyulu = 0x00000006;//"������¼
	public static final int TASK_mingrenyulu = 0x00000007;//������¼
	public static final int TASK_tuwentonghua = 0x00000008;//ͼ��ͯ��
	public static final int TASK_qitayulu = 0x00000009;//������¼
	
	public static final int TASK_SHOUYE_TUIJIAN = 0x00000010;//������¼
	
	public static final int JokeContentGallery_TASK_PIC_DOWNLOADING = 0x00000010;//������¼
}
