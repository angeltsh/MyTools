package tools;

import java.io.File;

public class Test {
	public static void main(String[] args) {

		String root = "F:/���粿/�ź�/10��ģ��/�������ģ�31201-34800��/33601-34200������/Test";

		int start = 33787;
		int end = 34200;

		for (int i = start; i <= end; i++) {
			File file = new File(root + "/" + i);
			// ����ļ��в������򴴽�
			if (!file.exists() && !file.isDirectory()) {
				System.out.println("//������");
				file.mkdir();
			} else {
				System.out.println("//Ŀ¼����");
			}
		}

	}
}
