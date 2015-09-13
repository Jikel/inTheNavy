package battleship;

public class Coordinate {
	private int x;
	private int y;

	public Coordinate(int y, int x) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Coordonee [x = " + x + ", y = " + y + "]";
	}

}
