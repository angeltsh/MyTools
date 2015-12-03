package cvs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CVSTool {

	private InputStreamReader fr = null;
	private BufferedReader br = null;

	public CVSTool(String f) throws IOException {
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
		CVSTool test = new CVSTool("C:/Users/Administrator/Desktop/test.csv");
		List<List<String>> cvsList = test.readCSVFile();

		int start = 0;
		if ((cvsList.get(0).get(0).toLowerCase()).equals("keyid")) {
			start = 1;
		} else {
			start = 0;
		}

		// StringBuffer sql = new StringBuffer("INSERT INTO `payroll` ( ");
		//
		// // 添加关键字
		// for (int i = start; i < cvsList.get(0).size(); i++) {
		// sql.append("`" + cvsList.get(0).get(i) + "`");
		// if (i != (cvsList.get(0).size() - 1)) {
		// sql.append(",");
		// }
		//
		// }
		// sql.append(") VALUES");

		StringBuffer sql = new StringBuffer(
				"INSERT INTO `payroll` (`unit`,`dept`,`role`,`name`,`contracts`,`degree`,`pv`,`bv`,`sv`,`subsidy`,`seniority`,`dv`,`ov`,`rv`,`mv`,`performance`,`sales`,`piecewage`,`reward`,`punish`,`floating`,`attendancedays`,`publicdays`,`deduction`,`attendance`,`meals`,`phonebill`,`ot`,`extrawage`,`shouldv`,`socialsecurity`,`accumulationfund`,`insurancesfund`,`taxablewages`,`tax`,`realv`,`bank`,`cash`,`salarydate` ) VALUES");

		// 添加列表值
		for (int j = 1; j < cvsList.size(); j++) {
			sql.append("(");

			for (int i = start; i < cvsList.get(0).size(); i++) {
				String value = "";
				if (i < cvsList.get(j).size()) {
					value = cvsList.get(j).get(i);
				}

				if (i <= start + 5 || i == (cvsList.get(0).size() - 1)) {
					sql.append("'" + value + "'");
				} else {
					value = "0" + value;
					sql.append(Double.parseDouble(value));
				}

				// System.out.println(cvsList.get(0).size());
				if (i != (cvsList.get(0).size() - 1)) {
					sql.append(",");
				}

			}
			sql.append(")");

			if (j != (cvsList.size() - 1)) {
				sql.append(",");
			}

		}
		System.out.println(sql);
	}
}
