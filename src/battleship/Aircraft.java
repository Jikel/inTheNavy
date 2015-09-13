package battleship;

import java.util.Arrays;

public class Aircraft extends Boat {

	private PieceOfBoat piece[] = new PieceOfBoat[super.getSize()];

	public Aircraft(boolean isHorizontal) {
		super("Porte-avions", 5, isHorizontal);

		boatConstruct();
	}

	public Aircraft() {
		super("Porte-avions", 5, true);
		boatConstruct();
	}

	public void boatConstruct() {
		piece[0] = new PieceOfBoat(new Coordinate(0, 0), 3);
		piece[1] = new PieceOfBoat(new Coordinate(0, 0), 3);
		piece[2] = new PieceOfBoat(new Coordinate(0, 0), 2);
		piece[3] = new PieceOfBoat(new Coordinate(0, 0), 3);
		piece[4] = new PieceOfBoat(new Coordinate(0, 0), 3);
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
		return "Porte-avions [piece=" + Arrays.toString(piece) + "]";
	}

}
