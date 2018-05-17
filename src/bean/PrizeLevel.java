package bean;

public class PrizeLevel {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrizeLevelName() {
		return prizeLevelName;
	}
	public void setPrizeLevelName(String prizeLevelName) {
		this.prizeLevelName = prizeLevelName;
	}
	public PrizeLevel(String id, String prizeLevelName) {
		super();
		this.id = id;
		this.prizeLevelName = prizeLevelName;
	}
	@Override
	public String toString() {
		return "PrizeLevel [id=" + id + ", prizeLevelName=" + prizeLevelName + "]";
	}
	private String id;
	private String prizeLevelName;
	
}
