package org.intschools.northern.constants;
/**
 * 
 * @author fanky10
 * Utils class to check if given url belongs to specific resource type
 *
 */
public class BrowserUrlUtils {

	public static boolean isPDF(String url) {
		return url.endsWith("pdf");
	}

	public static boolean isAnImage(String url) {
		return url.endsWith("jpg") || url.endsWith("jpeg") || url.endsWith("png") || url.endsWith("gif");
	}
}
