package com.joke.template.weibooperation;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import weibo4andriod.Comment;
import weibo4andriod.Status;
import weibo4andriod.Weibo;
import weibo4andriod.WeiboException;
import weibo4andriod.androidexamples.OAuthConstant;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.joke.template.handlertype.MsgHandlerType;
import com.joke.template.utils.StringUtils;

public class WeiboOperation {
	// �����߳�GlobeConfData
	private static Handler mHandler = null;
	private static String accessToken = null;
	private static String accessSecret = null;
	private static String shareMsg = null;
	private static String mFilepath = null;

	private static boolean sendOrCommentAtSametime = false;// Ϊfalseʱ��ʾֻ�������ۣ�����ֻת��΢��
	private static int weiboType = MsgHandlerType.WEIBO_TYPE_SEND;
	private static WeiboInfo mWeiboInfo = null;

	/**
	 * 
	 * @param _mHandler
	 *            ������Ϣ�ľ��
	 * @param _shareMsg
	 *            ��Ҫ���͵���Ϣ
	 * @param filepath
	 *            ��Ҫ������ͼƬ
	 * @param _sendOrCommentAtSametime
	 *            �Ƿ�ͬʱ�������ۻ���΢��
	 * @param weibotype
	 *            ΢�������ͣ������֣�1��ת��΢����2������΢����3������΢��;��MsgHandlerType���ﶨ��
	 */
	public static void sendWeibo(Handler _mHandler, String _shareMsg,
			String filepath, boolean _sendOrCommentAtSametime, int weibotype,
			WeiboInfo _mWeiboInfo) {
		mHandler = _mHandler;
//		GlobeConfData.getInstance().LoadConf();
//		accessToken = GlobeConfData.getInstance().GetSinaWeicoToken();
//		accessSecret = GlobeConfData.getInstance().GetSinaWeicoTokenSecret();
		shareMsg = _shareMsg;
		mFilepath = filepath;
		sendOrCommentAtSametime = _sendOrCommentAtSametime;

		weiboType = weibotype;
		mWeiboInfo = _mWeiboInfo;

		Thread thread = new Thread(sendWeibo);
		thread.start();
	}

	/**
	 * ����΢��
	 */
	static Runnable sendWeibo = new Runnable() {

		@Override
		public void run() {

			if (accessToken == null || accessSecret == null) {
				Message msg = new Message();
				msg.obj = -1;
				msg.what = MsgHandlerType.MSG_TYPE_INIT_ERROR;
				mHandler.sendMessage(msg);
			} else {
				Weibo weibo = OAuthConstant.getInstance().getWeibo();
				weibo.setToken(accessToken, accessSecret);
				try {
					if (MsgHandlerType.WEIBO_TYPE_COMMENT == weiboType) {// ����΢������

						sendWeiboComment(weibo);

						if (sendOrCommentAtSametime) {// ͬʱ����һ��΢��
							reTweetWeibo(weibo);
						}
					} else if (MsgHandlerType.WEIBO_TYPE_RETWEET == weiboType) {// ת��΢��
						reTweetWeibo(weibo);
						
						if (sendOrCommentAtSametime) {// ͬʱ�������۷���
							sendWeiboComment(weibo);
						}
					} else if (MsgHandlerType.WEIBO_TYPE_SEND == weiboType) {// ����һ����΢��
						
						sendWeibo(weibo);
					} else {
						// TODO δ֪���ͣ��ݲ�������

					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("WeiboPub", e.getMessage());
					Message msg = new Message();
					msg.what = MsgHandlerType.MSG_TYPE_INIT_ERROR;
					msg.obj = -1;
					mHandler.sendMessage(msg);
				} catch (WeiboException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("WeiboPub", e.getMessage());
					Message msg = new Message();
					msg.what = MsgHandlerType.MSG_TYPE_INIT_ERROR;
					msg.obj = -1;
					mHandler.sendMessage(msg);
				}
			}
		}
	};

	/**
	 * ����΢��
	 * 
	 * @param weibo
	 * @throws UnsupportedEncodingException
	 * @throws WeiboException
	 */
	private static void sendWeibo(Weibo weibo)
			throws UnsupportedEncodingException, WeiboException {
		String msg = shareMsg.toString();
		if (msg.getBytes().length != msg.length()) {
			msg = URLEncoder.encode(msg, "UTF-8");
		}

		Status status = null;
		if (StringUtils.isBlank(mFilepath)) {
			status = weibo.updateStatus(msg);
		} else {
			File file = new File(mFilepath);
			status = weibo.uploadStatus(msg, file);
		}

		if (status != null) {
			Message msg2 = new Message();
			msg2.what = MsgHandlerType.MSG_TYPE_INIT_FINISH;
			msg2.obj = 1;
			mHandler.sendMessage(msg2);
		}
	}

	/**
	 * ����΢��
	 * 
	 * @param weibo
	 * @throws WeiboException
	 * @throws UnsupportedEncodingException
	 */
	private static void sendWeiboComment(Weibo weibo) throws WeiboException,
			UnsupportedEncodingException {
		String msg = shareMsg.toString();
		if (msg.getBytes().length != msg.length()) {
			msg = URLEncoder.encode(msg, "UTF-8");
		}

		Comment status = weibo.updateComment("pppppppppp",
				mWeiboInfo.m_id + "", null);

		if (status != null) {
			Message msg2 = new Message();
			msg2.what = MsgHandlerType.MSG_TYPE_INIT_FINISH;
			msg2.obj = 1;
			mHandler.sendMessage(msg2);
		}
	}

	/**
	 * ת��΢��
	 * 
	 * @param weibo
	 * @throws WeiboException
	 * @throws UnsupportedEncodingException
	 */
	private static void reTweetWeibo(Weibo weibo) throws WeiboException,
			UnsupportedEncodingException {
		String msg = shareMsg.toString();
		if (msg.getBytes().length != msg.length()) {
			msg = URLEncoder.encode(msg, "UTF-8");
		}

		Status status = weibo.repost(mWeiboInfo.m_id + "", msg);
		if (status != null) {
			Message msg2 = new Message();
			msg2.what = MsgHandlerType.MSG_TYPE_INIT_FINISH;
			msg2.obj = 1;
			mHandler.sendMessage(msg2);
		}
	}
}
