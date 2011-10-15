package com.joke.template.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtils 
{
	public static boolean isBlank(String input){
		if (input == null || "".equals(input))
			return true;
		
		for (int i = 0; i < input.length(); i++ ) {
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' ){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param input
	 * @return
	 */
	public static String cleanSpecialCharacter( String input){
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(input);
		
		return m.replaceAll("");
	}
	
	/**
	 * The unicode convert from gbk to utf8
	 * @param gbk String;
	 * @return utf8 String
	 * @throws UnsupportedEncodingException
	 */
	public static String gbk2Utf(String gbk) throws UnsupportedEncodingException {
		char[] c = gbk.toCharArray();
		byte[] fullByte = new byte[3*c.length];
		for (int i=0; i<c.length; i++) {
			String binary = Integer.toBinaryString(c[i]);
			StringBuffer sb = new StringBuffer();
			int len = 16 - binary.length();
			//ǰ�油��
			for(int j=0; j<len; j++){
		    		sb.append("0");
		    	}
			sb.append(binary);
			//����λ���ﵽ��24λ3���ֽ�
			sb.insert(0, "1110");
	        	sb.insert(8, "10");
	        	sb.insert(16, "10");
	       		fullByte[i*3] = Integer.valueOf(sb.substring(0, 8), 2).byteValue();//�������ַ�����������
	        	fullByte[i*3+1] = Integer.valueOf(sb.substring(8, 16), 2).byteValue();
	        	fullByte[i*3+2] = Integer.valueOf(sb.substring(16, 24), 2).byteValue();
		}
		//ģ��UTF-8�������վ��ʾ
		System.out.println(new String(fullByte,"UTF-8"));
		return new String(fullByte,"UTF-8");
	}
	
	// ȥ��&#8230;
	// ���������ַ�
	public static String StringFilter(String str) throws PatternSyntaxException {
		// ֻ������ĸ������
		// String regEx = "[^a-zA-Z0-9]";
		// ��������������ַ� &ldquo; &rdquo;
		String regEx = "[`~!@#$%^&*()+|{}':;',<>\\[\\]./?~@#��%����&*��������+|{}����������������]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
}