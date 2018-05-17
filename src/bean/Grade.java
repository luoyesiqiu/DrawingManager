package bean;

public class Grade {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Grade(String id, String gradeName) {
		super();
		this.id = id;
		this.gradeName = gradeName;
	}
	@Override
	public String toString() {
		return "Grade [id=" + id + ", gradeName=" + gradeName + "]";
	}
	private String id;
	private String gradeName;
	
}
