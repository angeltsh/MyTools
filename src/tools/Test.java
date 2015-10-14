package tools;

import java.io.File;

public class Test {
	public static void main(String[] args) {

		String root = "F:/网络部/张浩/10万模型/网络中心（31201-34800）/33601-34200杨闻轶/Test";

		int start = 33787;
		int end = 34200;

		for (int i = start; i <= end; i++) {
			File file = new File(root + "/" + i);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				System.out.println("//不存在");
				file.mkdir();
			} else {
				System.out.println("//目录存在");
			}
		}

	}
}
