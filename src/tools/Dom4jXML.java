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
	public int createXMLFile(String filename) {

		int returnValue = 0;

		Document document = DocumentHelper.createDocument();
		Element booksElement = document.addElement("books");
		booksElement.addComment("This is a test for dom4j, holen, 2004.9.11");
		Element bookElement = booksElement.addElement("book");
		/** 加入show属性内容 */
		bookElement.addAttribute("show", "yes");
		/** 加入title节点 */
		Element titleElement = bookElement.addElement("title");
		/** 为title设置内容 */
		titleElement.setText("Dom4j Tutorials");

		/** 类似的完成后两个book */
		bookElement = booksElement.addElement("book");
		bookElement.addAttribute("show", "yes");
		titleElement = bookElement.addElement("title");
		titleElement.setText("Lucene Studing");
		bookElement = booksElement.addElement("book");
		bookElement.addAttribute("show", "no");
		titleElement = bookElement.addElement("title");
		titleElement.setText("Lucene in Action");

		/** 加入owner节点 */
		Element ownerElement = booksElement.addElement("owner");
		ownerElement.setText("O'Reilly");
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
		return returnValue;
	}

	/**
	 * 格式化XML文档,并解决中文问题
	 * 
	 * @param filename
	 * @return
	 */
	public int formatXMLFile(String filename) {
		int returnValue = 0;
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(filename));
			XMLWriter writer = null;
			/** 格式化输出,类型IE浏览一样 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** 指定XML编码 */
			format.setEncoding("GBK");
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

		Dom4jXML temp = new Dom4jXML();
		System.out.println(temp.createXMLFile("g://holen.xml"));
		System.out.println(temp.formatXMLFile("g://holen.xml"));
	}
}
