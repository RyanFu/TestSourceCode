package com.joke.template.handlertype;

public class MsgHandlerType {
	public static final int MSG_ID_TEST = 1;		//����
	
	public static final int MSG_TYPE_INIT_START = 11200;//Ļ���߳����п�ʼ
	public static final int MSG_TYPE_INIT_RUNNING = 11201;//Ļ���߳�����ʱ
	public static final int MSG_TYPE_INIT_FINISH = 11202;//Ļ���߳�������ֹ
	public static final int MSG_TYPE_INIT_ERROR = 11203;//Ļ���߳����д���
	
	//΢������
	public static final int WEIBO_TYPE_SEND = 11203;//����΢��
	public static final int WEIBO_TYPE_RETWEET = 11204;//ת��΢��
	public static final int WEIBO_TYPE_COMMENT = 11205;//΢������
	
	public static final int HEAD_IMG_DOWN_FINISH = 31;		//ͷ��ͼƬ�������
	public static final int BLOG_IMG_DOWN_FINISH = 32;		//����ͼƬ�������
	
	//download status
	public static final int downloadStatus_downloading = 2;
	public static final int downloadStatus_Completed = 0;
	public static final int downloadStatus_IsExist = 1;
	public static final int downloadStatus_error = -1;
	public static final int downloadStatus_Completed_Onepage_GetData = 3;
	public static final int downloadStatus_Completed_Onepage_Delay_download_next = 4;
	public static final int Exit_album_delay = 5;
	public static final int download_COVER_Status_Completed = 10;
	
	//download method, you can select single thread to download or multi thread to download
	public static final int MULIT_DOWNLOAD = 6;
	public static final int SINGLE_DOWNLOAD = 7;
	public static final int SINGLE_COVER_DOWNLOAD = 77;
	public static final int SINGLE_COVER_DOWNLOAD_EXIST = 78;
	
	public static final int TASK_YULU_LIST_WEBVIEW_ONCLICK = 0x00010001;
}


