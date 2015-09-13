package battleship;

import java.util.Arrays;

public class TorpedoBoat extends Boat {

	private PieceOfBoat piece[] = new PieceOfBoat[super.getSize()];
	private int torpedo;

	public TorpedoBoat(boolean isHorizontal) {
		super("Torpilleur", 2, isHorizontal);
		boatConstruct();
		torpedo = 2;
		// TODO Auto-generated constructor stub
	}

	public TorpedoBoat() {
		super("Torpilleur", 2, true);
		boatConstruct();
		torpedo = 2;
	}

	public void boatConstruct() {
		piece[0] = new PieceOfBoat(new Coordinate(0, 0), 1);
		piece[1] = new PieceOfBoat(new Coordinate(0, 0), 2);
	}

	public void setTorpedo(int torpedo) {
		this.torpedo = torpedo;
	}

	public int getTorpedo() {
		return torpedo;
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
		return "Torpilleur [piece=" + Arrays.toString(piece) + ", torpille ="
				+ torpedo + "]";
	}
}
