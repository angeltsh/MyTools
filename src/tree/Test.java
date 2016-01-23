package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 把Tree进行树形显示，详细示例见页面
 *
 */
public class Test {
	public static void main(String[] args) {

		List<Model> list = new ArrayList<Model>();
		list.add(new Model(1, 0, "1"));
		list.add(new Model(7, 0, "7"));
		list.add(new Model(2, 1, "2"));
		list.add(new Model(3, 1, "3"));
		list.add(new Model(4, 2, "4"));
		list.add(new Model(6, 3, "6"));
		list.add(new Model(5, 6, "5"));
		list.add(new Model(8, 6, "8"));
		list.add(new Model(9, 7, "9"));
		list.add(new Model(10, 9, "10"));
		list.add(new Model(11, 10, "11"));

		// 构建tree
		TreeMap tree = new TreeMap(list);

		// 遍历tree
		System.out.println(tree.find(0).getChildren());

	}
}
