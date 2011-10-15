package com.joke.template.net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.joke.template.activity.JokeContentGalleryActivity;
import com.joke.template.logs.SuperLogs;
import com.joke.template.utils.FileUtils;

public class Parser {

	// ----------------------------------------------�������

	public static String getContentOfPage(String url) {
		// TODO Auto-generated method stub
		StringBuffer sb = null;
		try {
			URL ur = new URL(url);
			InputStream ss = ur.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(ss));
			String s = null;
			sb = new StringBuffer();
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();
			ss.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getContent(sb);
	}

	private static String getContent(StringBuffer sb) {
		// TODO Auto-generated method stub
		int start = 0;
		
		if(sb != null){
			int end = sb.indexOf("<body>") + "<body>".length();
			if (start != -1 && end != -1) {
				return sb.substring(start, end);
			}
		}
		return null;
	}

	private static String filter(String content) {
		// ȥ������script
		StringBuffer stringBuffer = new StringBuffer(content);
		String startScriptTag = "<div style=\"float:right;\">";
		String endScriptTag = "</script>--></div>";
		int starScript = stringBuffer.indexOf(startScriptTag);
		int endScript = stringBuffer.indexOf(endScriptTag, starScript);
		if (starScript != -1 && endScript != -1)
			stringBuffer.replace(starScript, endScript + endScriptTag.length(),
					"");

		// ȥ������
		String starthrefTag = "<a href=\"http://www.5time.cn/html";
		String endhrefTag = "(ԭ������)</a>";
		int starthref = stringBuffer.indexOf(starthrefTag);
		int endhref = stringBuffer.indexOf(endhrefTag, starthref);
		if (starthref != -1 && endhref != -1)
			stringBuffer.replace(starthref - 1, endhref + endhrefTag.length(),
					"");
		System.out.println(stringBuffer.toString());
		return stringBuffer.toString();
	}

	// ȥ��&#8230;
	// ���������ַ�
	public static String StringFilter(String str) throws PatternSyntaxException {
		//�ڹ���֮ǰ��Ҫ�滻�����е������ַ�  �������ţ�&ldquo;���������š� &rdquo;���������š�Ӣ������&quot;�ո�&nbsp;
		str = str.replaceAll("&ldquo;", "\"");
		str = str.replaceAll("&rdquo;", "\"");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&nbsp;", " ");
		// ֻ������ĸ������
		// String regEx = "[^a-zA-Z0-9]";
		// ��������������ַ�? <> / !
		String regEx = "[`~@#$%^&*()+|{}';',\\[\\].?~@#��%����&*��������+|{}������������]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	/**
	 * �����Ƭҳ���е���Ƭ����
	 * 
	 * @param pageHtml
	 *            ��Ƭҳ���ı�
	 * @return ��Ƭ���ӣ��������ڣ��򷵻�null
	 */
	
	private static Pattern PICTURE_SRC_LINK = Pattern.compile("src=\"(http:\\/\\/.*?)\"");
	public static void getPictureUrl(String pageHtml) {
		Matcher m = PICTURE_SRC_LINK.matcher(pageHtml);
		if (m.find()) {
			String picUrl = m.group(1);
			if(picUrl != null){
				String imageType = FileUtils.getUrlFileType(picUrl);
				if( imageType.equalsIgnoreCase(".png") || 
					imageType.equalsIgnoreCase(".jpg")|| 
					imageType.equalsIgnoreCase(".jpeg")|| 
					imageType.equalsIgnoreCase(".gif") ||
					imageType.contains("aid") ||
					picUrl.contains("pic.baohe.com")
					){
					
					System.out.println("Type:" + imageType + "    picurl=" + picUrl);
					JokeContentGalleryActivity.picUrltList.add(picUrl);
				}
			}
			int srcStart = pageHtml.indexOf("src");
			if(srcStart != -1)
				getPictureUrl(pageHtml.substring(srcStart + 3));
//			return m.group(1);
		} 
	}
	
	/**
	 * ��ȡ����url֮���������Ϣ
	 * @param html
	 */
	public static void getTextFromHtml(String html) {
		int count = JokeContentGalleryActivity.picUrltList.size();
		if(count > 1){
			//��ȡÿһ��url֮ǰ��text��Ϣ
			
			for(int i = 1; i < count; i++){
				String  textString = null;
				if(i == 1){
					String firstUrlString = JokeContentGalleryActivity.picUrltList.get(i);
					int start = 0;
					int end = html.indexOf(firstUrlString) - "<img src=\"".length();
					if(end != -1){
						textString = html.substring(start, end);
					}
				}else{
					String startUrlString = JokeContentGalleryActivity.picUrltList.get(i - 1);
					String endUrlString = JokeContentGalleryActivity.picUrltList.get(i);
					int start = html.indexOf(startUrlString);
					int end = html.indexOf(endUrlString);
					if(start != -1 && end != -1 && start < end){
						textString = html.substring(start, end);
					}
					SuperLogs.info("*******************************************" + startUrlString);
					SuperLogs.info(textString);
					SuperLogs.info("*******************************************" + endUrlString);
				}
				JokeContentGalleryActivity.picBeforeText.add(textString);
			}
		}
		
	}

	/**
	 * ��һЩ�����Ŵ�html�滻���ı�
	 * ������˫���š�ʡ�Ժš��ո��Լ�������
	 * @param str
	 * @return
	 */
	public static String quotFilter(String str) {
		str = str.replaceAll("&ldquo;", "\"");
		str = str.replaceAll("&rdquo;", "\"");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("&#8230;", "...");
		str = str.replaceAll("&rsquo;", "\'");//&rsquo;
		return str;
	}
	
	private final static String regxpForHtml = "<([^>]*)>"; // ����������<��ͷ��>��β�ı�ǩ
	/**
	 * 
	 * �������ܣ�����������"<"��ͷ��">"��β�ı�ǩ
	 * <p>
	 * 
	 * @param str
	 * @return String
	 */
	public static String filterHtml(String str) {
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * �������ܣ�����ָ����ǩ
	 * <p>
	 * 
	 * @param str
	 * @param tag
	 *            ָ����ǩ
	 * @return String
	 */
	public static String fiterHtmlTag(String str, String tag) {
		String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 
	 * �������ܣ��滻ָ���ı�ǩ
	 * <p>
	 * 
	 * @param str
	 * @param beforeTag
	 *            Ҫ�滻�ı�ǩ
	 * @param tagAttrib
	 *            Ҫ�滻�ı�ǩ����ֵ
	 * @param startTag
	 *            �±�ǩ��ʼ���
	 * @param endTag
	 *            �±�ǩ�������
	 * @return String
	 * @�磺�滻img��ǩ��src����ֵΪ[img]����ֵ[/img]
	 */
	public static String replaceHtmlTag(String str, String beforeTag,
			String tagAttrib, String startTag, String endTag) {
		String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer();
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
					.group(1));
			if (matcherForAttrib.find()) {
				matcherForAttrib.appendReplacement(sbreplace, startTag
						+ matcherForAttrib.group(1) + endTag);
			}
			matcherForTag.appendReplacement(sb, sbreplace.toString());
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 
	 * �������ܣ��滻�����������ʾ
	 * <p>
	 * 
	 * @param input
	 * @return String
	 */
	public String replaceTag(String input) {
		if (!hasSpecialChars(input)) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			default:
				filtered.append(c);
			}
		}
		return (filtered.toString());
	}

	/**
	 * 
	 * �������ܣ��жϱ���Ƿ����
	 * <p>
	 * @param input
	 * @return boolean
	 */
	public boolean hasSpecialChars(String input) {
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i <= input.length() - 1; i++) {
				c = input.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}
