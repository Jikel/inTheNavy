package battleship;

import java.util.Arrays;

public class Battleship extends Boat {

	private PieceOfBoat piece[] = new PieceOfBoat[super.getSize()];

	public Battleship(boolean isHorizontal) {
		super("Croiseur", 4, isHorizontal);
		boatConstruct();
		// TODO Auto-generated constructor stub
	}

	public Battleship() {
		super("Croiseur", 4, true);
		boatConstruct();
	}

	public void boatConstruct() {
		piece[0] = new PieceOfBoat(new Coordinate(0, 0), 2);
		piece[1] = new PieceOfBoat(new Coordinate(0, 0), 2);
		piece[2] = new PieceOfBoat(new Coordinate(0, 0), 2);
		piece[3] = new PieceOfBoat(new Coordinate(0, 0), 2);
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
		return "Croiseur [piece=" + Arrays.toString(piece) + "]";
	}
}
