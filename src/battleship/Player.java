package battleship;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author AHMED Ibrar, VAN HEURCK Tanguy & KESTELOOT Olivier
 *
 */
public class Player {
	private ArrayList<Boat> fleet;
	private ArrayList<Boat> enemyFleet;
	private String[][] playerBoard;
	private String[][] enemyBoard;
	private int boardSize;
	private Scanner sc;
	private String playerName;

	// constructor
	public Player(String playerName) {
		this.playerName = playerName;
		fleet = new ArrayList<Boat>();
		boardSize = 11;
		playerBoard = new String[boardSize][boardSize];
		enemyBoard = new String[boardSize][boardSize];
	}

	// getters
	public Boat getBoat(int positionFleet) {
		return fleet.get(positionFleet);
	}

	public int getFleetSize() {
		return fleet.size();
	}

	public int getEnemyFleetSize() {
		return enemyFleet.size();
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getBoard(Coordinate coord) {
		return playerBoard[coord.getY()][coord.getX()];
	}

	public String getEnemyBoard(Coordinate coord) {
		return enemyBoard[coord.getY()][coord.getX()];
	}

	// setters
	public void setPlayerBoard(Coordinate coord, String modification) {
		playerBoard[coord.getY()][coord.getX()] = modification;
	}

	public void setEnemyBoard(Coordinate coord, String modification) {
		enemyBoard[coord.getY()][coord.getX()] = modification;
	}

	// initialization methods
	/**
	 * Methode creant une flotte pour le joueur actif
	 */
	public void createFleet() {
		fleet.add(new Aircraft());
		fleet.add(new Battleship());
		fleet.add(new Destroyer());
		fleet.add(new Submarine());
		fleet.add(new TorpedoBoat());
	}

	/**
	 * Methode creant deux plateaux vierges: un pour que le joueur pose ses
	 * bateaux et l'autre pour qu'il voit ou il a deja attaque l'adversaire
	 */
	public void initializeBoard() {
		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 11; j++) {
				playerBoard[i][j] = "-";
				enemyBoard[i][j] = "-";
			}
		}
		playerBoard[0][0] = "  ";
		enemyBoard[0][0] = "  ";
		for (int i = 1; i < 10; i++) {
			playerBoard[i][0] = Integer.toString(i) + " ";
			enemyBoard[i][0] = Integer.toString(i) + " ";
			playerBoard[0][i] = Integer.toString(i);
			enemyBoard[0][i] = Integer.toString(i);
		}
		playerBoard[0][10] = "10";
		enemyBoard[0][10] = "10";
		playerBoard[10][0] = "10";
		enemyBoard[10][0] = "10";
	}

	/**
	 * Methode permettant d'effacer les coordonnees d'un bateau avant un
	 * deplacement
	 * 
	 * @see Boat#getSize()
	 * @see Boat#setCoordinate(int, Coordinate)
	 */
	public void zeroCoordinate(int positionFleet) {
		Coordinate zero = new Coordinate(0, 0);
		for (int i = 0; i < fleet.get(positionFleet).getSize(); i++) {
			fleet.get(positionFleet).setCoordinate(i, zero);
		}
	}

	// printing methods
	/**
	 * Methode affichant le plateau de jeu du joueur actif avec sa flotte
	 * restante
	 * 
	 * @see Player#getPlayerName()
	 */
	public void printPlayerBoard() {
		System.out.println(getPlayerName());
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				System.out.print(playerBoard[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Methode affichant le plateau sur lequel le joueur actif attaque
	 */
	public void printEnemyBoard() {
		System.out.println("ADVERSAIRE");
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				System.out.print(enemyBoard[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Methode affichant les coordonnees et les points de vie de chaque partie
	 * du bateau
	 * 
	 * @param positionFleet
	 * @see Boat#getCoordinate(int)
	 * @see Boat#getSize()
	 * @see Boat#getLife(int)
	 */
	public void printBoat(int positionFleet) {
		for (int i = 0; i < fleet.get(positionFleet).getSize(); i++) {
			System.out.println(fleet.get(positionFleet).getCoordinate(i)
					+ "  Life: " + fleet.get(positionFleet).getLife(i));
		}
	}

	// placement methods
	/**
	 * Methode permettant de placer un bateau sur le plateau du joueur actif
	 * 
	 * @param positionFleet
	 * @param coord
	 * @see Player#conflictPosition(int, Coordinate)
	 * @see Player#setPlayerBoard(Coordinate, String)
	 * @see Boat#getSize()
	 * @see Boat#getIsHorizontal()
	 * @see Boat#setCoordinate(int, Coordinate)
	 */
	public void boatPosition(int positionFleet, Coordinate coord) {
		sc = new Scanner(System.in);
		int choice = 0;
		while (conflictPosition(positionFleet, coord)) {
			System.out.println("Mauvaise coordonee");
			System.out.println("Entrez la coordonnee x");
			choice = sc.nextInt();
			coord.setX(choice);
			System.out.println("Entrez la coordonnee y");
			choice = sc.nextInt();
			coord.setY(choice);
		}

		for (int i = 0; i < fleet.get(positionFleet).getSize(); i++) {
			// ATTENTION REGARDE L'EXPLICATION SUR LA FEUILLE DE COURS
			Coordinate counter = new Coordinate(0, 0);
			if (fleet.get(positionFleet).getIsHorizontal()) {
				counter.setX(coord.getX() + i);
				counter.setY(coord.getY());
				fleet.get(positionFleet).setCoordinate(i, counter);
				setPlayerBoard(counter, "#");
			} else {
				counter.setX(coord.getX());
				counter.setY(coord.getY() + i);
				fleet.get(positionFleet).setCoordinate(i, counter);
				setPlayerBoard(counter, "#");
			}
		}
	}

	/**
	 * Methode permettant au joueur actif de placer ses bateaux en debut de
	 * partie
	 * 
	 * @see Player#printPlayerBoard()
	 * @see Player#boatPosition(int, Coordinate)
	 * @see Boat#getName()
	 * @see Boat#setIsHorizaontal(boolean)
	 */
	public void positionFleet() {
		Coordinate coord = new Coordinate(0, 0);
		char firstLetter = 'i';
		int scannerPosition = 0;
		Scanner sc2 = new Scanner(System.in);
		for (int i = 0; i < fleet.size(); i++) {
			printPlayerBoard();
			System.out.println("Entrez les coordonnees de votre "
					+ fleet.get(i).getName());

			do {
				System.out.println("Votre " + fleet.get(i).getName()
						+ " est-il horizontal ? (O / N):");
				firstLetter = sc2.next().charAt(0);

			} while ((firstLetter != 'n') && (firstLetter != 'N')
					&& (firstLetter != 'o') && (firstLetter != 'O'));

			if ((firstLetter == 'o') || (firstLetter == 'O')) {
				fleet.get(i).setIsHorizaontal(true);
			} else {
				fleet.get(i).setIsHorizaontal(false);
			}
			firstLetter = 'i';

			System.out.println("Entrez la coordonnee x");
			scannerPosition = sc2.nextInt();
			coord.setX(scannerPosition);
			System.out.println("Entrez la coordonnee y");
			scannerPosition = sc2.nextInt();
			coord.setY(scannerPosition);
			boatPosition(i, coord);
		}

	}

	// fighting methods

	/**
	 * Methode permettant d'effacer un bateau detruit du plateau de jeu
	 * 
	 * @param positionFleet
	 * @see Coordinate#getX()
	 * @see Coordinate#getY()
	 * @see Boat#getCoordinate(int)
	 * @see Player#setPlayerBoard(Coordinate, String)
	 */
	public void eraseBoat(int positionFleet) {
		Coordinate coord = new Coordinate(0, 0);
		for (int i = 0; i < fleet.get(positionFleet).getSize(); i++) {
			coord.setX(fleet.get(positionFleet).getCoordinate(i).getX());
			coord.setY(fleet.get(positionFleet).getCoordinate(i).getY());
			setPlayerBoard(coord, "-");
		}
	}

	/**
	 * Methode permettant de detruire un bateau et de le retirer du ArrayList
	 * fleet du joueur
	 * 
	 * @param positionFleet
	 * @see Player#eraseBoat(int)
	 */
	public void destroyBoat(int positionFleet) {
		eraseBoat(positionFleet);
		fleet.remove(positionFleet);
	}

	// test methods
	/**
	 * 
	 * @param positionFleet
	 * @param coord
	 * @return vrai si la coordonnee entree touche une case deja occupee par un
	 *         bateau du joueur ou si elle sort du plateau de jeu
	 * @see Boat#getIsHorizontal()
	 * @see Coordinate#setX(int)
	 * @see Coordinate#setY(int)
	 * @see Player#checkCollision(Coordinate)
	 * @see Player#outOfBoard(Coordinate)
	 */
	public boolean conflictPosition(int positionFleet, Coordinate coord) {
		boolean conflict = false;
		Coordinate test = new Coordinate(0, 0);
		for (int i = 0; i < fleet.get(positionFleet).getSize(); i++) {
			if (fleet.get(positionFleet).getIsHorizontal()) {
				test.setX(coord.getX() + i);
				test.setY(coord.getY());
				if ((outOfBoard(test)) || (checkCollision(test))) {
					conflict = true;
				}
			} else {
				test.setX(coord.getX());
				test.setY(coord.getY() + i);
				if ((outOfBoard(test)) || (checkCollision(test))) {
					conflict = true;
				}
			}
		}
		return conflict;
	}

	/**
	 * Methode verifiant si une coordonnee ne se trouve pas en dehors du plateau
	 * 
	 * @param coord
	 * @return vrai si la coordonnee entree sort du plateau de jeu
	 * @see Coordinate#getX()
	 * @see Coordinate#getY()
	 */
	public boolean outOfBoard(Coordinate coord) {
		boolean out = false;
		if ((coord.getX() < 1) || (coord.getX() > 10) || (coord.getY() < 1)
				|| (coord.getY() > 10))
			out = true;
		return out;
	}

	/**
	 * Methode verifiant s'il y a une partie de bateau sur une case
	 * 
	 * @param coord
	 * @return vrai si la coordonnee entree est deja occupee par un autre bateau
	 * @see Coordinate#getX()
	 * @see Coordinate#getY()
	 * @see Boat#getCoordinate(int)
	 */
	public boolean checkCollision(Coordinate coord) {
		boolean collision = false;
		// on parcourt le vecteur de bateau
		for (int i = 0; i < fleet.size(); i++) {
			// sur chaque bateau on vérifie les coordonnees
			for (int j = 0; j < fleet.get(i).getSize(); j++) {
				if ((coord.getX() == fleet.get(i).getCoordinate(j).getX())
						&& (coord.getY() == fleet.get(i).getCoordinate(j)
								.getY()))
					collision = true;
			}
		}
		return collision;
	}

	/*
	 * public boolean checkEnemyCollision(Coordinate coord) { boolean collision
	 * = false; // on parcourt le vecteur de bateau for (int i = 0; i <
	 * enemyFleet.size(); i++) { // sur chaque bateau on vérifie les coordonnees
	 * for (int j = 0; j < enemyFleet.get(i).getSize(); j++) { if ((coord.getX()
	 * == enemyFleet.get(i).getCoordinate(j).getX()) && (coord.getY() ==
	 * enemyFleet.get(i).getCoordinate(j) .getY())) collision = true; } } return
	 * collision; }
	 */

	/**
	 * Methode verifiant si le joueur actif a encore la possibilite de lancer
	 * une super torpille
	 * 
	 * @return vrai si le joueur peut encore tirer une torpille
	 * @see Player#getBoat(int)
	 * @see Player#getFleetSize()
	 * @see TorpedoBoat#getTorpedo()
	 */
	public boolean checkTorpedo() {
		boolean torpedo = false;
		for (int positionFleet = 0; positionFleet < getFleetSize(); positionFleet++) {
			if (getBoat(positionFleet) instanceof TorpedoBoat) {
				TorpedoBoat tb = (TorpedoBoat) getBoat(positionFleet);
				if (tb.getTorpedo() > 0) {
					torpedo = true;
				}
			}
		}
		return torpedo;
	}

	/**
	 * Methode verifiant si le joueur actif a encore la possibilite de lancer
	 * une mine
	 * 
	 * @return vrai si le joueur peut encore lancer une mine
	 * @see Player#getBoat(int)
	 * @see Player#getFleetSize()
	 * @see Submarine#getMine()
	 */
	public boolean checkMine() {
		boolean mine = false;
		for (int positionFleet = 0; positionFleet < getFleetSize(); positionFleet++) {
			if (getBoat(positionFleet) instanceof Submarine) {
				Submarine s = (Submarine) getBoat(positionFleet);
				if (s.getMine() > 0) {
					mine = true;
				}
			}
		}
		return mine;
	}

}
