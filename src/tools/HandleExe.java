package tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HandleExe {

	public static void main(String[] args) {

		// 截图示例
		getVideoImg("F:\\网络部\\张浩\\教学视频\\Anyprint软件+打印.mpg",
				"F:\\网络部\\张浩\\教学视频\\test.jpg", "00:00:20");

		// 转码示例
		parseVideoType("F:\\网络部\\张浩\\教学视频\\Anyprint软件+打印.mpg",
				"F:\\网络部\\张浩\\教学视频\\Anyprint软件+打印.mp4");

		// openWinExe("notepad");

	}

	/**
	 * 将视频进行格式转换
	 * 
	 * @param videoPath
	 *            原始文件绝对路径
	 * @param targetPath
	 *            目标文件路径
	 */
	public static void parseVideoType(String videoPath, String targetPath) {
		openExe(System.getProperty("user.dir") + "/exe/ffmpeg.exe -i "
				+ videoPath + " -y " + targetPath);
	}

	/**
	 * 视频截图
	 * 
	 * @param videoPath
	 *            视频路径
	 * @param imgPath
	 *            截图路径
	 * @param time
	 *            截图时间，格式 00:00:00
	 */
	public static void getVideoImg(String videoPath, String imgPath, String time) {
		openExe(System.getProperty("user.dir") + "/exe/ffmpeg.exe -i "
				+ videoPath + " -y -f  image2  -ss " + time + " -vframes 1  "
				+ imgPath);
	}

	/**
	 * 调用其他的可执行文件，例如：自己制作的exe，或是 下载 安装的软件.
	 * 
	 * @param command
	 */
	public static void openExe(String exePath) {

		Runtime rn = Runtime.getRuntime();
		Process p = null;

		try {
			p = rn.exec(exePath);

			InputStream stderr = p.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);

			String msg = null;
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
			br.close();

			int exitVal = p.waitFor();

		} catch (Exception e) {
			System.out.println("Error exec!");
		}

	}

	/**
	 * 用 Java 调用windows系统的exe文件，比如notepad，calc之类
	 * 
	 * @param command
	 */
	public static void openWinExe(String command) {

		openExe(command);

	}
}