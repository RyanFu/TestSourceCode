package com.joke.template;

import android.os.Message;


public interface ActivityStatus {

	public static final int INIT_SUCC = 0;
	public static final int INIT_FAIL = 1;
	public enum SUPER_ACTIVITY_STATUS{RUN,STOP};
	
	public SUPER_ACTIVITY_STATUS getActivityStatus();
	
	public void setActivityStatus(SUPER_ACTIVITY_STATUS status);

	public void onMsgData(Message msg);		//��Ϣ���˴���֪ͨ
	
	public int doneAsyncTask(int taskId);					//���߳�init��������
	
	public void	InitStart(int taskId);

	public void	InitFinish(int taskId, int init_status);

	void InitFinishError(int taskId, int init_status);

	int doneAsyncTask2(int taskId);

}
