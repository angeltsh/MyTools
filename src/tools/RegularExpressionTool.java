package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionTool {

	/**
	 * 验证邮箱是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		Pattern p = Pattern
				.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 验证手机号是否正确
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		Pattern p = Pattern.compile("^1[0-9][0-9]\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
}
