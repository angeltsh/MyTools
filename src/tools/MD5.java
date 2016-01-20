package tools;

import java.math.BigInteger;
import java.security.MessageDigest;

/** MD5加密工具 */
public class MD5 {

	/**
	 * 字符串MD5加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			str = new BigInteger(1, md.digest()).toString(16);
			// 如果生成数字未满32位，需要前面补0
			for (int i = 0; i < 32 - str.length(); i++)
				str = "0" + str;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

	public static void main(String[] args) {
		String s = "1,2015091416034256099120312,0,1,0,提交成功";
		System.out.println(s.substring(0, 1));
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}