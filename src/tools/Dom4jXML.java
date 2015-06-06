package tools;

import java.io.File;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jXML {

	public static int createXMLFile(String filename) {
		int returnValue = 0;

		Document document = DocumentHelper.createDocument();
		document.addComment("订单详细内容");

		Element root = document.addElement("OrderData");
		root.addAttribute("id", "keyId");

		// 订单编号
		root.addComment("订单编号");
		Element orderNo = root.addElement("OrderNo");
		orderNo.setText("TN123456789");

		// 订单金额
		root.addComment("订单价格");
		Element price = root.addElement("Price");
		price.addAttribute("unit", "￥");
		price.setText("50.00");

		root.addComment("左手拇指");
		Element imgL1 = root.addElement("imgL1");
		imgL1.setText("http://192.168.1.136:8080/3dmeijia/images/app-nails/test.jpg");

		root.addComment("左手食指");
		Element imgL2 = root.addElement("imgL2");
		imgL2.setText("http://192.168.1.136:8080/3dmeijia/images/app-nails/test.jpg");

		root.addComment("左手中指");
		Element imgL3 = root.addElement("imgL3");
		imgL3.setText("http://192.168.1.136:8080/3dmeijia/images/app-nails/test.jpg");

		root.addComment("左手无名指");
		Element imgL4 = root.addElement("imgL4");
		imgL4.setText("http://192.168.1.136:8080/3dmeijia/images/app-nails/test.jpg");

		root.addComment("左手小指");
		Element imgL5 = root.addElement("imgL5");
		imgL5.setText("http://192.168.1.136:8080/3dmeijia/images/app-nails/test.jpg");

		root.addComment("右手拇指");
		Element imgR1 = root.addElement("imgR1");
		imgR1.setText("http://192.168.1.136:8080/3dmeijia/images/app-nails/test.jpg");

		root.addComment("右手食指");
		Element imgR2 = root.addElement("imgR2");
		imgR2.setText("http://192.168.1.136:8080/3dmeijia/images/app-nails/test.jpg");

		root.addComment("右手中指");
		Element imgR3 = root.addElement("imgR3");
		imgR3.setText("http://192.168.1.136:8080/3dmeijia/images/app-nails/test.jpg");

		root.addComment("右手无名指");
		Element imgR4 = root.addElement("imgR4");
		imgR4.setText("http://192.168.1.136:8080/3dmeijia/images/app-nails/test.jpg");

		root.addComment("右手小指");
		Element imgR5 = root.addElement("imgR5");
		imgR5.setText("http://192.168.1.136:8080/3dmeijia/images/app-nails/test.jpg");

		try {
			/** 将document中的内容写入文件中 */
			XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)));
			writer.write(document);
			writer.close();
			/** 执行成功,需返回1 */
			returnValue = 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		formatXMLFile(filename);

		return returnValue;
	}

	/**
	 * 格式化XML文档,并解决中文问题
	 * 
	 * @param filename
	 * @return
	 */
	private static int formatXMLFile(String filename) {
		int returnValue = 0;
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(filename));
			XMLWriter writer = null;
			/** 格式化输出,类型IE浏览一样 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** 指定XML编码 */
			format.setEncoding("UTF-8");
			writer = new XMLWriter(new FileWriter(new File(filename)), format);
			writer.write(document);
			writer.close();
			/** 执行成功,需返回1 */
			returnValue = 1;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnValue;

	}

	public static void main(String[] args) {
		Dom4jXML.createXMLFile("g://holen.xml");
	}
}
