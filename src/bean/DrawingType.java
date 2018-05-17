package bean;

public class DrawingType {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDrawingTypeName() {
		return drawingTypeName;
	}
	public void setDrawingTypeName(String drawingTypeName) {
		this.drawingTypeName = drawingTypeName;
	}
	public DrawingType(String id, String drawingTypeName) {
		super();
		this.id = id;
		this.drawingTypeName = drawingTypeName;
	}
	@Override
	public String toString() {
		return "DrawingType [id=" + id + ", drawingTypeName=" + drawingTypeName + "]";
	}
	private String id;
	private String drawingTypeName;
}
