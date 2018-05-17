package bean;

public class Major {

	public String getMajorCode() {
		return majorCode;
	}
	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public Major(String majorCode, String majorName) {
		super();
		this.majorCode = majorCode;
		this.majorName = majorName;
	}
	@Override
	public String toString() {
		return "Major [majorCode=" + majorCode + ", majorName=" + majorName + "]";
	}
	private String majorCode;
	private String majorName;
}
