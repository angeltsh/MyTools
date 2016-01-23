package tree;

public class Model {
	private Integer keyId;
	private Integer pId;
	private String name;

	public Model(Integer keyId, Integer pId, String name) {
		this.keyId = keyId;
		this.pId = pId;
		this.name = name;
	}

	public Integer getKeyId() {
		return keyId;
	}

	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "KeyId: " + this.getKeyId() + ", PID: " + this.getpId()
				+ ", Name: " + this.getName() + "\n";
	}

}
