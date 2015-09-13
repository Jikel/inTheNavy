package battleship;

import java.util.Scanner;

/**
 * 
 * @author AHMED Ibrar, VAN HEURCK Tanguy & KESTELOOT Olivier
 * 
 */
public class ActionGame {

	// constructor
	public ActionGame() {

	}

	// selection action
	/**
	 * Methode permettant de choisir un bateau parmi ceux restant dans la flotte
	 * du joueur actif
	 * 
	 * @param player
	 * @return un entier qui correspond au type de bateau choisi par
	 *         l'utilisateur
	 * @see Player#getBoat(int)
	 * @see Player#getFleetSize()
	 * @see Boat#getName()
	 */
	public int chooseBoat(Player p) {
		int choice = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Votre flotte :");
		for (int i = 0; i < p.getFleetSize(); i++) {
			System.out.println(i + 1 + " " + p.getBoat(i).getName());
		}
		System.out.println("Choississez un bateau");
		choice = sc.nextInt();
		while ((choice < 1) || (choice > p.getFleetSize())) {
			System.out.println("Choix non valide : veuillez reessayer");
			choice = sc.nextInt();
		}
		return choice - 1;
	}

	// methode affichant le menu de demarrage
	/**
	 * Methode affichant le menu de lancement du jeu
	 */
	public void menuLancement() {
		System.out
				.println(" ______________________________________________________________");
		System.out.println("|         BATAILLE NAVALE AMELIOREE");
		System.out.println("|");
		System.out
				.println("|  Auteur:  AHMED Ibrar, VAN HEURCK Tanguy & KESTELOOT Olivier");
		System.out.println("|");
		System.out.println("|");
		System.out.println("|");
		System.out.println("|");
	}

	/**
	 * Methode presentant les options disponibles pour le joueur
	 * 
	 * @param p
	 * @return un char qui correspond a l'action choisie par le joueur
	 * @see Player#getPlayerName()
	 */
	public char menu(Player p) {
		char choix = '0';
		Scanner sc = new Scanner(System.in);
		System.out
				.println(" ______________________________________________________________");
		System.out.println("|    Tour de " + p.getPlayerName()
				+ "                                            |");
		System.out
				.println("|	                                                       |");
		System.out
				.println("|	1) Pour selectionner un bateau et le deplacer          |");
		System.out
				.println("|	2) Pour lancer une torpille                            |");
		System.out
				.println("|	3) Pour lancer une super-torpille                      |");
		System.out
				.println("|	4) Pour placer une mine                                |");
		System.out
				.println("|	5) Pour scanner une zone                               |");
		System.out
				.println("|	6) Pour verifier l'etat d'un bateau                    |");
		System.out
				.println("|______________________________________________________________|");
		choix = sc.next().charAt(0);
		while ((choix != '1') && (choix != '2') && (choix != '3')
				&& (choix != '4') && (choix != '5') && (choix != '6')) {
			System.out
					.println("Choix non valide veuillez reintroduire votre choix");
			choix = sc.next().charAt(0);
		}
		return choix;
	}

	/**
	 * Methode permettant de choisir une coordonnee
	 * 
	 * @param p
	 * @return un objet Coordinate choisi par le joueur avec un controle pour
	 *         verifier si la coordonnee choisie se trouve bien sur le plateau
	 *         de jeu
	 * @see Player#outOfBoard(Coordinate)
	 */
	public Coordinate chooseCoordinate(Player p) {
		Coordinate choice = new Coordinate(0, 0);
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez la coordonee x");
		choice.setX(sc.nextInt());
		System.out.println("Entrez la coordonee y");
		choice.setY(sc.nextInt());
		while (p.outOfBoard(choice)) {
			System.out
					.println("La case choisie est en dehors du plateau de jeu");
			System.out.println("Entrez la coordonee x");
			choice.setX(sc.nextInt());
			System.out.println("Entrez la coordonee y");
			choice.setY(sc.nextInt());
		}
		return choice;
	}

	// moving action
	/**
	 * 
	 * @param p
	 * @param enemy
	 * @see Player#boatPosition(int, Coordinate)
	 * @see Player#destroyBoat(int)
	 * @see Player#zeroCoordinate(int)
	 * @see ActionGame#chooseBoat(Player)
	 * @see ActionGame#mineExplosion(Player, Player, int, Coordinate)
	 */
	public void moveBoat(Player p, Player enemy) {
		int positionFleet = chooseBoat(p);
		Scanner sc = new Scanner(System.in);
		char choice = 'z';
		Coordinate buffer = p.getBoat(positionFleet).getCoordinate(0);
		Coordinate test = new Coordinate(0, 0);
		Coordinate erase = new Coordinate(0, 0);

		if (p.getBoat(positionFleet).getIsHorizontal()) {
			System.out.println("Gauche ou droite (G or D)");
			do {
				choice = sc.next().charAt(0);
			} while ((choice != 'd') && (choice != 'D') && (choice != 'g')
					&& (choice != 'G'));
			// deplacement vers la droite
			if ((choice == 'd') || (choice == 'D')) {
				test.setY(p.getBoat(positionFleet).getCoordinate(0).getY());
				test.setX(p.getBoat(positionFleet)
						.getCoordinate(p.getBoat(positionFleet).getSize() - 1)
						.getX() + 1);

				if ((p.outOfBoard(test) == false)
						&& (mineExplosion(p, enemy, positionFleet, test))) {
					p.destroyBoat(positionFleet);
				} else {
					if (((p.outOfBoard(test)) == false)
							&& (p.checkCollision(test) == false)) {
						p.setPlayerBoard(buffer, "-");
						p.zeroCoordinate(positionFleet);
						buffer.setX(buffer.getX() + 1);
						p.boatPosition(positionFleet, buffer);
					} else {
						System.out
								.println("Impossible de se deplacer sur cette case");
						System.out.println("Vous perdez votre tour");
					}
				}

			}
			// deplacement vers la gauche
			else {
				test.setY(p.getBoat(positionFleet).getCoordinate(0).getY());
				test.setX(p.getBoat(positionFleet).getCoordinate(0).getX() - 1);
				if (mineExplosion(p, enemy, positionFleet, test)) {
					p.destroyBoat(positionFleet);
				} else {
					if (((p.outOfBoard(test)) == false)
							&& (p.checkCollision(test) == false)) {
						erase.setY(test.getY());
						erase.setX(p
								.getBoat(positionFleet)
								.getCoordinate(
										p.getBoat(positionFleet).getSize() - 1)
								.getX());
						p.setPlayerBoard(erase, "-");
						p.zeroCoordinate(positionFleet);
						buffer.setX(buffer.getX() - 1);
						p.boatPosition(positionFleet, buffer);
					} else {
						System.out
								.println("Impossible de se deplacer sur cette case");
						System.out.println("Vous perdez votre tour");
					}
				}
			}
		} else {
			System.out.println("Haut ou bas (H or B)");
			do {
				choice = sc.next().charAt(0);
			} while ((choice != 'h') && (choice != 'H') && (choice != 'b')
					&& (choice != 'B'));
			// deplacement vers le haut
			if ((choice == 'h') || (choice == 'H')) {
				test.setY(p.getBoat(positionFleet).getCoordinate(0).getY() - 1);
				test.setX(p.getBoat(positionFleet).getCoordinate(0).getX());
				if (mineExplosion(p, enemy, positionFleet, test)) {
					p.destroyBoat(positionFleet);
				} else {
					if (((p.outOfBoard(test)) == false)
							&& (p.checkCollision(test) == false)) {
						erase.setY(p
								.getBoat(positionFleet)
								.getCoordinate(
										p.getBoat(positionFleet).getSize() - 1)
								.getY());
						erase.setX(test.getX());
						p.setPlayerBoard(erase, "-");
						p.zeroCoordinate(positionFleet);
						buffer.setY(buffer.getY() - 1);
						p.boatPosition(positionFleet, buffer);
					} else {
						System.out
								.println("Impossible de se deplacer sur cette case");
						System.out.println("Vous perdez votre tour");
					}
				}
			}
			// deplacement vers le bas
			else {
				test.setY(p.getBoat(positionFleet)
						.getCoordinate(p.getBoat(positionFleet).getSize() - 1)
						.getY() + 1);
				test.setX(p.getBoat(positionFleet).getCoordinate(0).getX());
				if ((p.outOfBoard(test) == false)
						&& (mineExplosion(p, enemy, positionFleet, test))) {
					p.destroyBoat(positionFleet);
				} else {
					if (((p.outOfBoard(test)) == false)
							&& (p.checkCollision(test) == false)) {
						erase.setY(buffer.getY());
						erase.setX(test.getX());
						p.setPlayerBoard(erase, "-");
						p.zeroCoordinate(positionFleet);
						buffer.setY(buffer.getY() + 1);
						p.boatPosition(positionFleet, buffer);
					} else {
						System.out
								.println("Impossible de se deplacer sur cette case");
						System.out.println("Vous perdez votre tour");
					}
				}
			}
		}

	}

	// fighting action
	/**
	 * Methode utilisee a chaque fois qu'une attaque touche un bateau ennemi.
	 * Elle enleve a la partie touchee un point de vie et si elle tombe a zero,
	 * elle detruit le bateau
	 * 
	 * @param p
	 * @param coord
	 * @see Player#destroyBoat(int)
	 * @see Player#boatPosition(int, Coordinate)
	 * @see Player#getFleetSize()
	 * @see Boat#getCoordinate(int)
	 * @see Boat#getLife(int)
	 * @see Boat#setLife(int, int)
	 * @see Boat#getName()
	 * @see Coordinate#getX()
	 * @see Coordinate#getY()
	 */
	public void damagedBoat(Player p, Coordinate coord) {
		for (int positionFleet = 0; positionFleet < p.getFleetSize(); positionFleet++) {
			// sur chaque bateau on verifie les coordonnees
			for (int j = 0; j < p.getBoat(positionFleet).getSize(); j++) {
				if ((coord.getX() == p.getBoat(positionFleet).getCoordinate(j)
						.getX())
						&& (coord.getY() == p.getBoat(positionFleet)
								.getCoordinate(j).getY())) {
					p.getBoat(positionFleet).setLife(j,
							p.getBoat(positionFleet).getLife(j) - 1);
				}

				if (p.getBoat(positionFleet).getLife(j) < 1) {
					System.out.println(p.getBoat(positionFleet).getName()
							+ " coule");
					p.destroyBoat(positionFleet);
					break;
				}
			}
		}
	}

	/**
	 * Methode permettant d'envoyer une torpille ordinaire qui enleve 1 point de
	 * vie en cas d'attaque reussie
	 * 
	 * @param player
	 * @param enemy
	 * @param coord
	 * @see Player#checkCollision(Coordinate)
	 * @see Player#setEnemyBoard(Coordinate, String)
	 * @see ActionGame#damagedBoat(Player, Coordinate)
	 */
	public void attack(Player player, Player enemy, Coordinate coord) {
		if (enemy.checkCollision(coord)) {
			System.out.println("Vous avez touche un bateau");
			damagedBoat(enemy, coord);
			player.setEnemyBoard(coord, "X");
		} else {
			System.out.println("Tir dans l'eau");
			player.setEnemyBoard(coord, "0");
		}
	}

	/**
	 * Methode permettant d'envoyer une super torpille qui coule immediatement
	 * un bateau en cas d'attaque reussie
	 * 
	 * @param player
	 * @param enemy
	 * @param coord
	 * @see Player#getFleetSize()
	 * @see Player#checkCollision(Coordinate)
	 * @see Player#setEnemyBoard(Coordinate, String)
	 * @see Player#getBoat(int)
	 * @see TorpedoBoat#getTorpedo()
	 * @see TorpedoBoat#setTorpedo(int)
	 * @see ActionGame#damagedBoat(Player, Coordinate)
	 */
	public void superTorpedo(Player player, Player enemy, Coordinate coord) {
		int n = enemy.getFleetSize();
		if (enemy.checkCollision(coord)) {
			System.out.println("Vous avez touche un bateau");
			while (enemy.getFleetSize() == n) {
				damagedBoat(enemy, coord);
			}
			player.setEnemyBoard(coord, "#");
		} else {
			System.out.println("Tir dans l'eau");
			player.setEnemyBoard(coord, "0");
		}
		// enlever une super torpille du stock Torpedo boat
		for (int positionFleet = 0; positionFleet < player.getFleetSize(); positionFleet++) {
			if (player.getBoat(positionFleet) instanceof TorpedoBoat) {
				TorpedoBoat tb = (TorpedoBoat) player.getBoat(positionFleet);
				tb.setTorpedo(tb.getTorpedo() - 1);
			}
		}

	}

	/**
	 * Methode permettant d'envoyer une mine qui reste presente sur la carte et
	 * qui detruit le bateau qui se deplace sur la case cible ou qui est present
	 * au moment de l'attaque
	 * 
	 * @param player
	 * @param enemy
	 * @param coord
	 * @see Player#getFleetSize()
	 * @see Player#checkCollision(Coordinate)
	 * @see Player#setEnemyBoard(Coordinate, String)
	 * @see Player#getBoat(int)
	 * @see Submarine#getMine()
	 * @see Submarine#setMine(int)
	 * @see ActionGame#damagedBoat(Player, Coordinate)
	 */
	public void mine(Player player, Player enemy, Coordinate coord) {
		int n = enemy.getFleetSize();
		if (enemy.checkCollision(coord)) {
			System.out.println("Vous avez touché un bateau");
			while (enemy.getFleetSize() == n) {
				damagedBoat(enemy, coord);
			}
			player.setEnemyBoard(coord, "#");
		} else {
			System.out.println("Tir dans l'eau");
			player.setEnemyBoard(coord, "*");
		}
		// enlever une mine du stock du Submarine
		for (int positionFleet = 0; positionFleet < player.getFleetSize(); positionFleet++) {
			if (player.getBoat(positionFleet) instanceof Submarine) {
				Submarine s = (Submarine) player.getBoat(positionFleet);
				s.setMine(s.getMine() - 1);
			}
		}
	}

	/**
	 * Methode permettant de savoir si une mine se trouve sur la coordonnee en
	 * parametre
	 * 
	 * @param player
	 * @param enemy
	 * @param positionFleet
	 * @param coord
	 * @return vrai si coord correspond a l'emplacement d'une mine
	 * @see Player#getEnemyBoard(Coordinate)
	 * @see Player#setEnemyBoard(Coordinate, String)
	 * @see Player#getBoat(int)
	 * @see Boat#getName()
	 */
	public boolean mineExplosion(Player player, Player enemy,
			int positionFleet, Coordinate coord) {
		boolean explosion = false;
		if (enemy.getEnemyBoard(coord) == "*") {
			System.out.println("Votre "
					+ player.getBoat(positionFleet).getName()
					+ " a touche une mine");
			System.out.println(player.getBoat(positionFleet).getName()
					+ " coule");
			explosion = true;
			enemy.setEnemyBoard(coord, "-");
		}
		return explosion;
	}

	/**
	 * Methode permettant de scanner une case du plateau de l'adversaire ainsi
	 * que toutes celles adjacentes
	 * 
	 * @param player
	 * @param enemy
	 * @param coord
	 * @see Coordinate#setX(int)
	 * @see Coordinate#setY(int)
	 * @see Coordinate#getX(int)
	 * @see Coordinate#getY(int)
	 * @see Player#outOfBoard(Coordinate)
	 * @see Player#getBoard(Coordinate)
	 * @see Player#setEnemyBoard(Coordinate, String)
	 */
	public void scan(Player player, Player enemy, Coordinate coord) {
		Coordinate buffer[] = new Coordinate[9];
		for (int i = 0; i < 9; i++) {
			buffer[i] = new Coordinate(0, 0);
		}
		// position initiale
		buffer[0].setX(coord.getX());
		buffer[0].setY(coord.getY());
		if (player.outOfBoard(buffer[0]) == false)
			player.setEnemyBoard(buffer[0], enemy.getBoard(buffer[0]));
		player.setEnemyBoard(coord, enemy.getBoard(coord));
		// position initiale x+1
		buffer[1].setX(coord.getX() + 1);
		buffer[1].setY(coord.getY());
		if (player.outOfBoard(buffer[1]) == false)
			player.setEnemyBoard(buffer[1], enemy.getBoard(buffer[1]));
		// position initiale x+1 et y+1
		buffer[2].setX(coord.getX() + 1);
		buffer[2].setY(coord.getY() + 1);
		if (player.outOfBoard(buffer[2]) == false)
			player.setEnemyBoard(buffer[2], enemy.getBoard(buffer[2]));
		// position initiale x+1 et y-1
		buffer[3].setX(coord.getX() + 1);
		buffer[3].setY(coord.getY() - 1);
		if (player.outOfBoard(buffer[3]) == false)
			player.setEnemyBoard(buffer[3], enemy.getBoard(buffer[3]));
		// position initiale y-1
		buffer[4].setX(coord.getX());
		buffer[4].setY(coord.getY() - 1);
		if (player.outOfBoard(buffer[4]) == false)
			player.setEnemyBoard(buffer[4], enemy.getBoard(buffer[4]));
		// position initiale y+1
		buffer[5].setX(coord.getX());
		buffer[5].setY(coord.getY() + 1);
		if (player.outOfBoard(buffer[5]) == false)
			player.setEnemyBoard(buffer[5], enemy.getBoard(buffer[5]));
		// position initiale x-1 et y+1
		buffer[6].setX(coord.getX() - 1);
		buffer[6].setY(coord.getY() + 1);
		if (player.outOfBoard(buffer[6]) == false)
			player.setEnemyBoard(buffer[6], enemy.getBoard(buffer[6]));
		// position initiale x-1
		buffer[7].setX(coord.getX() - 1);
		buffer[7].setY(coord.getY());
		if (player.outOfBoard(buffer[7]) == false)
			player.setEnemyBoard(buffer[7], enemy.getBoard(buffer[7]));
		// position initiale x-1 et y-1
		buffer[8].setX(coord.getX() - 1);
		buffer[8].setY(coord.getY() - 1);
		if (player.outOfBoard(buffer[8]) == false)
			player.setEnemyBoard(buffer[8], enemy.getBoard(buffer[8]));
	}

}
