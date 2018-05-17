package bean;


public class Drawing {

    public String getStudentNumber() {
	return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getDrawingName() {
		return drawingName;
	}
	public void setDrawingName(String drawingName) {
		this.drawingName = drawingName;
	}
	public String getDrawingCategory() {
		return drawingCategory;
	}
	public void setDrawingCategory(String drawingCategory) {
		this.drawingCategory = drawingCategory;
	}
	public String getDrawingSize() {
		return drawingSize;
	}
	public void setDrawingSize(String drawingSize) {
		this.drawingSize = drawingSize;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public String getPrizeLevel() {
		return prizeLevel;
	}
	public void setPrizeLevel(String prizeLevel) {
		this.prizeLevel = prizeLevel;
	}
	public String getPrizeDate() {
		return prizeDate;
	}
	public void setPrizeDate(String prizeDate) {
		this.prizeDate = prizeDate;
	}
	public String getPrizePhoto() {
		return prizePhoto;
	}
	public void setPrizePhoto(String prizePhoto) {
		this.prizePhoto = prizePhoto;
	}
	public String getDrawingPhoto() {
		return drawingPhoto;
	}
	public void setDrawingPhoto(String drawingPhoto) {
		this.drawingPhoto = drawingPhoto;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDrawingDesc() {
		return drawingDesc;
	}
	public void setDrawingDesc(String drawingDesc) {
		this.drawingDesc = drawingDesc;
	}
	
	public Drawing(String id, String studentNumber, String drawingName, String drawingCategory, String drawingSize,
			String createDate, String prizeName, String prizeLevel, String prizeDate, String prizePhoto,
			String drawingPhoto, String drawingDesc,String publishDate) {
		super();
		this.id = id;
		this.studentNumber = studentNumber;
		this.drawingName = drawingName;
		this.drawingCategory = drawingCategory;
		this.drawingSize = drawingSize;
		this.createDate = createDate;
		this.prizeName = prizeName;
		this.prizeLevel = prizeLevel;
		this.prizeDate = prizeDate;
		this.prizePhoto = prizePhoto;
		this.drawingPhoto = drawingPhoto;
		this.drawingDesc = drawingDesc;
		this.publishDate=publishDate;
	}



	private String id;
	private String studentNumber;
	private String drawingName;
	private String drawingCategory;
	private String drawingSize;
	private String createDate;
	private String prizeName;
	private String prizeLevel;
	private String prizeDate;
	private String prizePhoto;
	private String drawingPhoto;
	private String drawingDesc;
	private String publishDate;
	
	@Override
	public String toString() {
		return "Drawing [id=" + id + ", studentNumber=" + studentNumber + ", drawingName=" + drawingName
				+ ", drawingCategory=" + drawingCategory + ", drawingSize=" + drawingSize + ", createDate=" + createDate
				+ ", prizeName=" + prizeName + ", prizeLevel=" + prizeLevel + ", prizeDate=" + prizeDate
				+ ", prizePhoto=" + prizePhoto + ", drawingPhoto=" + drawingPhoto + ", drawingDesc=" + drawingDesc
				+ ", publishDate=" + publishDate + "]";
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

}
