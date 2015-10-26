package tools;

public class TimeTool {

	public static boolean isBefore(String time1, String time2) {
		return !(isBigger(time1, time2));
	}

	public static boolean isAfter(String time1, String time2) {
		return isBigger(time1, time2);
	}

	private static boolean isBigger(String time1, String time2) {

		boolean flag = false;
		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		java.util.Calendar c2 = java.util.Calendar.getInstance();
		try {
			c1.setTime(df.parse(time1));
			c2.setTime(df.parse(time2));
		} catch (java.text.ParseException e) {
			System.err.println("格式不正确");
			flag = false;
		}
		int result = c1.compareTo(c2);
		if (result == 0) {
			System.out.println("相等");
			flag = true;
		} else if (result < 0) {
			System.out.println("小于");
			flag = false;
		} else {
			System.out.println("大于");
			flag = true;
		}

		return flag;
	}
}
