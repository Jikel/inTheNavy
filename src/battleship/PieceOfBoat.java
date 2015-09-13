package battleship;

public class PieceOfBoat {
	private Coordinate coord;
	private int life;
	
	public PieceOfBoat(Coordinate coord, int life) {
		super();
		this.coord = coord;
		this.life = life;
	}

	public Coordinate getCoord() {
		return coord;
	}

	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	@Override
	public String toString() {
		return "Partie de bateau [coord=" + coord + ", point de resistance =" + life + "]";
	}
	

}
