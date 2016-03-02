package cvs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kucun {

	private InputStreamReader fr = null;
	private BufferedReader br = null;

	public Kucun(String f) throws IOException {
		fr = new InputStreamReader(new FileInputStream(f), "GBK");
	}

	/**
	 * 解析csv文件 到一个list中 每个单元个为一个String类型记录，每一行为一个list。 再将所有的行放到一个总list中
	 */
	public List<List<String>> readCSVFile() throws IOException {
		br = new BufferedReader(fr);
		String rec = null;// 一行
		String str;// 一个单元格
		List<List<String>> listFile = new ArrayList<List<String>>();
		try {
			// 读取一行
			while ((rec = br.readLine()) != null) {
				Pattern pCells = Pattern
						.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,*");
				Matcher mCells = pCells.matcher(rec);
				List<String> cells = new ArrayList<String>();// 每行记录一个list
				// 读取每个单元格
				while (mCells.find()) {
					str = mCells.group();
					str = str.trim();
					if (str.length() > 0) {
						char slast = str.charAt(str.length() - 1);
						int lastIndex = str.length();
						int startIndex = 0;

						if (slast == ',') {
							lastIndex = lastIndex - 1;
						}
						if ('"' == str.charAt(0)) {
							startIndex += 1;
						}
						if ('"' == str.charAt(lastIndex - 1)) {
							lastIndex -= 1;
						}
						str = str.substring(startIndex, lastIndex);
						cells.add(str);
					}

				}

				if (cells.size() > 0) {
					listFile.add(cells);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fr != null) {
				fr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return listFile;
	}

	// 测试方法
	public static void main(String[] args) throws IOException {
		String rootPath = "C:/Users/admin/Desktop/";
		String csvFileName = "csv.csv";
		String resultName = "update.sql";

		CVSTool test = new CVSTool(rootPath + csvFileName);
		List<List<String>> cvsList = test.readCSVFile();

		StringBuffer sql = consoleData(cvsList);

		writeTofile(sql, rootPath + resultName, false);

	}

	// 处理数据
	public static StringBuffer consoleData(List<List<String>> cvsList) {
		StringBuffer sql = new StringBuffer();

		// 添加列表值
		for (int i = 1; i < cvsList.size(); i++) {
			sql.append("update depot_current set fdcount="
					+ cvsList.get(i).get(1) + " where protcodeid='"
					+ cvsList.get(i).get(0) + "';\r\n");
		}

		return sql;
	}

	// 写入文件
	/**
	 * 将数据写入到指定文件中
	 */
	public static void writeTofile(StringBuffer data, String resultPath,
			boolean isAppend) {
		File writefile = null;
		FileWriter fw = null;
		try {
			// 通过这个对象来判断是否向文本文件中追加内容
			writefile = new File(resultPath);
			// 如果文本文件不存在则创建它
			if (writefile.exists() == false) {
				writefile.createNewFile();
				writefile = new File(resultPath); // 重新实例化
			}
			fw = new FileWriter(writefile, isAppend);
			fw.write(data.toString());
			fw.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
