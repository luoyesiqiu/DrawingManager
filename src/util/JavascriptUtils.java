package util;

public class JavascriptUtils {
	/**
	 * ����javascript��ʾ��
	 * @param msg
	 */
	public static String genAlertMsg(String msg){
		return genJsString("alert(\""+msg+"\");");
	}
	
	/**
	 * ����javascript����
	 * @param msg
	 */
	public static String genJsString(String text){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("<script type=\"text/javascript\">\n");
		stringBuilder.append(text);
		stringBuilder.append("</script>\n");
		
		return stringBuilder.toString();
	}
}
