package com.joke.template.tasks;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;

public class TimerTasks extends TimerTask {
	private static Handler mHandler = null;
	private static Timer mTimer = null;
	private static int timeTaskType = -1;
	private static int stopTaskType = -1000;
	private static TimerTasks mTimerTask;

	/**
	 * ÿ��һ��ʱ��ͻ�ִ���������ֱ��ֹͣ _mHandler�Ǹ��𴫵���Ϣ��ȥ
	 */
	public TimerTasks(Handler _mHandler, int _taskType, int _stopTaskType) {
		mHandler = _mHandler;
		timeTaskType = _taskType;
		stopTaskType= _stopTaskType;
	}

	@Override
	public void run() {
		//TODO ��Ҫִ�еĶ���
		//TODO 
		Message msg = new Message();
		msg.what = timeTaskType;
		mHandler.sendMessage(msg);
	}

	public static void startThisTask(Handler _mHandler, int _taskType, int _stopTaskType) {

		if (mTimer == null) {
			mTimerTask = new TimerTasks(_mHandler, _taskType, stopTaskType);
			// mTimerTask.scheduledExecutionTime();
			mTimer = new Timer();

			// ��һ������Ϊִ�е�mTimerTask
			// �ڶ�������Ϊ�ӳٵ�ʱ�� ����д1000����˼��mTimerTask���ӳ�1��ִ��
			// ����������Ϊ���ִ��һ�� ����д1000��ʾÿ1��ִ��һ��mTimerTask��Run����
			mTimer.schedule(mTimerTask, 1000, 1000);
		}

	}

	public static void stopThisTask() {
		// ������ر�mTimer �� mTimerTask
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		if (mTimerTask != null) {
			mTimerTask = null;
		}

		/** ID���� **/

		// ���﷢��һ��ֻ��what�յ���Ϣ
		mHandler.sendEmptyMessage(stopTaskType);
	}
}
