package util;

public class JavascriptUtils {
	/**
	 * 生成javascript提示框
	 * @param msg
	 */
	public static String genAlertMsg(String msg){
		return genJsString("alert(\""+msg+"\");");
	}
	
	/**
	 * 生成javascript代码
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
