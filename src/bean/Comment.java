package bean;

public class Comment {
	public Comment(String id, String drawingId, String nickname, String content, String createDate) {
		super();
		this.id = id;
		this.drawingId = drawingId;
		this.nickname = nickname;
		this.content = content;
		this.createDate = createDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDrawingId() {
		return drawingId;
	}
	public void setDrawingId(String drawingId) {
		this.drawingId = drawingId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", drawingId=" + drawingId + ", nickname=" + nickname + ", content=" + content
				+ ", createDate=" + createDate + "]";
	}
	private String id;
	private String drawingId;
	private String nickname;
	private String content;
	private String createDate;
}
