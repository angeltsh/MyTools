package tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeMap {

	private Model element;
	private Map<Integer, TreeMap> children;// keyId对应的元素

	public TreeMap(Model element) {
		this.element = element;
	}

	public TreeMap(List<Model> list) {
		this.element = new Model(0, 0, "Root");
		for (int i = 0; i < list.size(); i++) {
			Model element = list.get(i);
			TreeMap ctree = this.find(element.getpId());
			if (ctree != null) {
				ctree.getChildren().put(list.get(i).getKeyId(),
						new TreeMap(list.get(i)));
			} else {
				System.err.println("=====输入的数据有错误,父分类不存在:<KeyId>"
						+ element.getKeyId());
			}
		}
	}

	public Model getElement() {
		return element;
	}

	public void setElement(Model element) {
		this.element = element;
	}

	public Map<Integer, TreeMap> getChildren() {
		if (children == null) {
			children = new HashMap<Integer, TreeMap>();
		}
		return children;
	}

	public void setChildren(Map<Integer, TreeMap> children) {
		this.children = children;
	}

	public TreeMap find(Integer keyId) {
		TreeMap result = null;
		if (this.element.getKeyId() == keyId) {
			result = this;
		} else {
			Iterator<Integer> it = this.getChildren().keySet().iterator();
			while (it.hasNext()) {
				result = this.getChildren().get(it.next()).find(keyId);
				if (result != null) {
					break;
				}
			}
		}
		return result;
	}

	@Override
	public String toString() {
		String str = this.getElement().toString();

		Iterator<Integer> it = this.getChildren().keySet().iterator();
		while (it.hasNext()) {
			str += this.getChildren().get(it.next()).toString();
		}
		return str;
	}
}
