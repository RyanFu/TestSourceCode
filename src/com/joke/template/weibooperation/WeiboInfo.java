package com.joke.template.weibooperation;

import java.util.List;




//�鼮������Ϣ
public class WeiboInfo
{
	public WeiboInfo(long nID)
	{
		m_id = nID;
	}
	
	
	public long	m_id = 0;
	public long m_authod_id = 0;
	public String m_sauthor = "";			//����
	public String m_sAuthorImgUrl = "";		//����ͷ��URL
	public String m_sContent = "";			//΢������
	public String m_sContentPicUrl = "";	//΢��ͼƬ
	public String m_sContentPicUrl_big = "";	//΢����ͼƬ
	
	public long   m_sub_id = 0;					//��΢������ID
	public String m_sSubAuthor = "";			//��΢������
	public String m_sSubContent = "";			//��΢������
	public String m_sSubContentPicUrl = "";		//��΢��ͼƬ
	public String m_sSubContentPicUrl_big = "";	//��΢����ͼƬ
	public List<String> m_tag_list;

	public String m_sTime = "";				//����ʱ��
	public String m_source = "";
	public boolean m_is_vip = false;
	

	
};