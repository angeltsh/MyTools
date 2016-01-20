package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 数据库表转换成javaBean对象小工具
 * 
 * <description> 生成文件在项目根目录下，已序列化 </description>
 * <ol>
 * <li>bean属性按原始数据库字段经过去掉下划线,并大写处理首字母等等.</li>
 * <li>生成的bean带了数据库的字段说明.</li>
 * <li>各位自己可以修改此工具用到项目中去.</li>
 * </ol>
 */
public class TableToBean {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TableToBean t = new TableToBean("com.xscz.entity", false,
				"C:/Users/Admin/Desktop/entity");
		t.setUrl("jdbc:mysql://192.168.1.83:3306/xueshengchuangzao?characterEncoding=utf8&user=root&password=root");
		// t.tableToEntity("race_class");
		// t.tableToEntity("race_sign_user");
		t.tableToEntity("race_product");
		// t.databaseToEntity("scpt_utf8");

	}

	private String tablename = "";
	private String[] colnames;
	private String[] colTypes;
	private int[] colSizes; // 列名大小
	private int[] colScale; // 列名小数精度
	private boolean importUtil = false;
	private boolean importSql = false;
	private boolean importMath = false;

	private String packageStr;
	private boolean isSerializable;
	private String rooFilePath;
	private String url = "jdbc:mysql://192.168.1.83:3306/scpt_utf8?characterEncoding=utf8&user=root&password=root";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// JDBC连接
	private Connection getConnection() {
		try {
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param packageStr
	 *            包位置
	 * @param isSerializable
	 *            是否序列化
	 */
	public TableToBean(String packageStr, boolean isSerializable, String path) {
		this.packageStr = packageStr;
		this.isSerializable = isSerializable;
		this.rooFilePath = path;
	}

	public void databaseToEntity(String dataBase) {
		// 数据连Connection获取,JDBC
		Connection conn = getConnection();

		String strsql = "select table_name from information_schema.tables where table_schema='"
				+ dataBase + "'";

		try {
			PreparedStatement pstmt = conn.prepareStatement(strsql);
			ResultSet rs = pstmt.executeQuery();
			String tableName = "";

			while (rs.next()) {
				tableName = rs.getString(1);
				System.out.println(tableName);
				tableToEntity(tableName);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 *            各位按自己的
	 */
	public void tableToEntity(String tName) {
		tablename = tName;
		// 数据连Connection获取,JDBC
		Connection conn = getConnection();

		String strsql = "SELECT * FROM " + tablename;// +" WHERE ROWNUM=1"
														// 读一行记录;

		if (conn == null) {
			return;
		}

		try {
			System.out.println(strsql);
			PreparedStatement pstmt = conn.prepareStatement(strsql);
			pstmt.executeQuery();
			ResultSetMetaData rsmd = pstmt.getMetaData();
			int size = rsmd.getColumnCount(); // 共有多少列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			colScale = new int[size];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				rsmd.getCatalogName(i + 1);
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1).toLowerCase();
				colScale[i] = rsmd.getScale(i + 1);
				System.out.println(rsmd.getCatalogName(i + 1));
				if ("datetime".equals(colTypes[i])) {
					importUtil = true;
				}
				if ("image".equals(colTypes[i]) || "text".equals(colTypes[i])) {
					importSql = true;
				}
				if (colScale[i] > 0) {
					importMath = true;
				}
				colSizes[i] = rsmd.getPrecision(i + 1);
			}
			String content = parse(colnames, colTypes, colSizes);
			try {
				FileWriter fw = new FileWriter(rooFilePath + File.separator
						+ upCap(tablename) + ".java");
				PrintWriter pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析处理(生成实体类主体代码)
	 */
	private String parse(String[] colNames, String[] colTypes, int[] colSizes) {
		StringBuffer sb = new StringBuffer();
		sb.append("\rpackage " + packageStr + ";");
		if (isSerializable) {
			sb.append("\r\nimport java.io.Serializable;\r\n");
		}
		if (importUtil) {
			sb.append("import java.util.Date;\r\n");
		}
		if (importSql) {
			sb.append("import java.sql.*;\r\n\r\n");
		}
		if (importMath) {
			sb.append("import java.math.*;\r\n\r\n");
		}
		// 表注释
		processColnames(sb);
		sb.append("public class " + upCap(tablename));
		if (isSerializable) {
			sb.append(" implements Serializable ");
		}
		sb.append("{\r\n");
		processAllAttrs(sb);
		processAllMethod(sb);
		sb.append("}\r\n");
		System.out.println(sb.toString());
		return sb.toString();

	}

	/**
	 * 处理列名,把空格下划线'_'去掉,同时把下划线后的首字母大写 要是整个列在3个字符及以内,则去掉'_'后,不把"_"后首字母大写.
	 * 同时把数据库列名,列类型写到注释中以便查看,
	 * 
	 * @param sb
	 */
	private void processColnames(StringBuffer sb) {
		sb.append("\r\n/** " + tablename + "\r\n");
		String colsiz = "";
		for (int i = 0; i < colnames.length; i++) {
			colsiz = colSizes[i] <= 0 ? "" : (colScale[i] <= 0 ? "("
					+ colSizes[i] + ")" : "(" + colSizes[i] + "," + colScale[i]
					+ ")");
			sb.append("\t" + colnames[i].toUpperCase() + "    "
					+ colTypes[i].toUpperCase() + colsiz + "\r\n");
			char[] ch = colnames[i].toCharArray();
			char c = 'a';
			if (ch.length > 3) {
				for (int j = 0; j < ch.length; j++) {
					c = ch[j];
					if (c == '_') {
						if (ch[j + 1] >= 'a' && ch[j + 1] <= 'z') {
							ch[j + 1] = (char) (ch[j + 1] - 32);
						}
					}
				}
			}
			String str = new String(ch);
			colnames[i] = str.replaceAll("_", "");
		}
		sb.append("*/\r\n");
	}

	/**
	 * 生成所有的方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set"
					+ upCap(colnames[i])
					+ "("
					+ oracleSqlType2JavaType(colTypes[i], colScale[i],
							colSizes[i]) + " " + lowerCap(colnames[i])
					+ "){\r\n");
			sb.append("\t\tthis." + lowerCap(colnames[i]) + "="
					+ lowerCap(colnames[i]) + ";\r\n");
			sb.append("\t}\r\n");

			sb.append("\tpublic "
					+ oracleSqlType2JavaType(colTypes[i], colScale[i],
							colSizes[i]) + " get" + upCap(colnames[i])
					+ "(){\r\n");
			sb.append("\t\treturn " + lowerCap(colnames[i]) + ";\r\n");
			sb.append("\t}\r\n");
		}
	}

	/**
	 * 解析输出属性
	 * 
	 * @return
	 */
	private void processAllAttrs(StringBuffer sb) {
		if (isSerializable) {
			sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");
		}
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tprivate "
					+ oracleSqlType2JavaType(colTypes[i], colScale[i],
							colSizes[i]) + " " + lowerCap(colnames[i])
					+ ";\r\n");
		}
		sb.append("\r\n");
	}

	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private String upCap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	/**
	 * 把输入字符串的首字母改成小写
	 * 
	 * @param str
	 * @return
	 */
	private String lowerCap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'A' && ch[0] <= 'Z') {
			ch[0] = (char) (ch[0] + 32);
		}
		return new String(ch);
	}

	/**
	 * Oracle
	 * 
	 * @param sqlType
	 * @param scale
	 * @return
	 */
	private String oracleSqlType2JavaType(String sqlType, int scale, int size) {
		if (sqlType.equals("integer") || sqlType.equals("int")) {
			return "Integer";
		} else if (sqlType.equals("long")) {
			return "Long";
		} else if (sqlType.equals("float") || sqlType.equals("float precision")
				|| sqlType.equals("double")
				|| sqlType.equals("double precision")) {
			return "BigDecimal";
		} else if (sqlType.equals("number") || sqlType.equals("decimal")
				|| sqlType.equals("numeric") || sqlType.equals("real")) {
			return scale == 0 ? (size < 10 ? "Integer" : "Long") : "BigDecimal";
		} else if (sqlType.equals("varchar") || sqlType.equals("varchar2")
				|| sqlType.equals("char") || sqlType.equals("nvarchar")
				|| sqlType.equals("nchar")) {
			return "String";
		} else if (sqlType.equals("datetime") || sqlType.equals("date")
				|| sqlType.equals("timestamp")) {
			return "Date";
		}
		return null;
	}

}