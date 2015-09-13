package battleship;

import java.util.ArrayList;

public abstract class Boat {
	private String name;
	private int size;
	private boolean isHorizontal;

	// private boolean isOperational;
	// private ArrayList<PieceOfBoat> piece;

	public Boat(String name, int size, boolean isHorizontal) {
		this.name = name;
		this.size = size;
		this.isHorizontal = isHorizontal;
		// isOperational = true;
	}

	/*
	 * public void destroyShip() { isOperational = false; }
	 */

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public boolean getIsHorizontal() {
		return isHorizontal;
	}

	public void setIsHorizaontal(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}

	/*
	 * public boolean isOperational() { return isOperational; }
	 * 
	 * public void setOperational(boolean isOperational) { this.isOperational =
	 * isOperational; }
	 */

	public abstract Coordinate getCoordinate(int position);

	public abstract void setCoordinate(int position, Coordinate coord);

	public abstract int getLife(int position);

	public abstract void setLife(int position, int life);

	// public abstract void boatConstruct();

	/*
	 * public void initLifeBoat(int life) { piece.add(new PieceOfBoat(new
	 * Coordinate(0, 0), life)); }
	 */

	public abstract String toString();

}
