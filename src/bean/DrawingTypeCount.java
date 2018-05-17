package bean;

public class DrawingTypeCount {
	public DrawingTypeCount( int drawingSum, int prizeSum) {
		this.drawingSum = drawingSum;
		this.prizeSum = prizeSum;
	}
	public int getDrawingSum() {
		return drawingSum;
	}
	public void setDrawingSum(int drawingSum) {
		this.drawingSum = drawingSum;
	}
	public int getPrizeSum() {
		return prizeSum;
	}
	public void setPrizeSum(int prizeSum) {
		this.prizeSum = prizeSum;
	}
	private int drawingSum;
	private int prizeSum;
}
