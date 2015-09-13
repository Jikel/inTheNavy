package battleship;

import java.util.Arrays;

public class Submarine extends Boat {

	private PieceOfBoat piece[] = new PieceOfBoat[super.getSize()];
	private int mine;

	public Submarine(boolean isHorizontal) {
		super("Sous-marin", 3, isHorizontal);
		boatConstruct();
		mine = 5;
		// TODO Auto-generated constructor stub
	}

	public Submarine() {
		super("Sous-marin", 3, true);
		boatConstruct();
		mine = 2;
	}

	public int getMine() {
		return mine;
	}

	public void setMine(int mine) {
		this.mine = mine;
	}

	public void boatConstruct() {
		piece[0] = new PieceOfBoat(new Coordinate(0, 0), 2);
		piece[1] = new PieceOfBoat(new Coordinate(0, 0), 2);
		piece[2] = new PieceOfBoat(new Coordinate(0, 0), 2);
	}

	public int getLife(int position) {
		return piece[position].getLife();
	}

	public void setLife(int position, int life) {
		piece[position].setLife(life);
	}

	public Coordinate getCoordinate(int position) {
		return piece[position].getCoord();
	}

	public void setCoordinate(int position, Coordinate coord) {
		piece[position].setCoord(coord);
	}

	@Override
	public String toString() {
		return "Sous-marin [piece=" + Arrays.toString(piece) + ", mine=" + mine
				+ "]";
	}

}
