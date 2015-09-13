package battleship;

import java.util.Scanner;

public class LancerPartie {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		// JFrame m = new Menu();
		Scanner sc = new Scanner(System.in);
		ActionGame a = new ActionGame();
		char choice = '0';
		int positionFleet = 0;
		Coordinate coord = new Coordinate(0, 0);
		
		a.menuLancement();

		System.out.println("|  Veuillez entrer le nom du joueur 1:");
		Player player1 = new Player(sc.nextLine());
		player1.initializeBoard();
		player1.createFleet();
		player1.positionFleet();

		System.out.println("|  Veuillez entrez le nom du joueur 2:");
		Player player2 = new Player(sc.nextLine());
		player2.initializeBoard();
		player2.createFleet();
		player2.positionFleet();

		// boucle qui se resoud tant qu'un joueur n'a pas perdu toute sa flotte
		while ((player1.getFleetSize() > 0) && (player2.getFleetSize() > 0)) {
			// tour du joueur 1
			player1.printEnemyBoard();
			player1.printPlayerBoard();
			choice = a.menu(player1);
			// action si le joueur desire verifier l'etat de ses bateaux
			while (choice == '6') {
				positionFleet = a.chooseBoat(player1);
				player1.printBoat(positionFleet);
				player1.printEnemyBoard();
				player1.printPlayerBoard();
				choice = a.menu(player1);
			}

			switch (choice) {
			case '1':
				a.moveBoat(player1, player2);
				break;
			case '2':
				coord = a.chooseCoordinate(player1);
				a.attack(player1, player2, coord);
				break;
			case '3':
				if (player1.checkTorpedo()) {
					coord = a.chooseCoordinate(player1);
					a.superTorpedo(player1, player2, coord);
				} else {
					System.out.println("Vous n'avez plus de super torpille");
					System.out.println("Vous perdez votre tour");
				}
				break;
			case '4':
				if (player1.checkMine()) {
					coord = a.chooseCoordinate(player1);
					a.mine(player1, player2, coord);
				} else {
					System.out.println("Vous n'avez plus de mine");
					System.out.println("Vous perdez votre tour");
				}
				break;
			case '5':
				coord = a.chooseCoordinate(player1);
				a.scan(player1, player2, coord);
				break;
			}
			Thread.sleep(2500);
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();

			// tour du joueur 2
			if ((player1.getFleetSize() > 0) && (player2.getFleetSize() > 0)) {
				player2.printEnemyBoard();
				player2.printPlayerBoard();
				choice = a.menu(player2);
				// action si le joueur desire verifier l'etat de ses bateaux
				while (choice == '6') {
					positionFleet = a.chooseBoat(player2);
					player2.printBoat(positionFleet);
					player2.printEnemyBoard();
					player2.printPlayerBoard();
					choice = a.menu(player2);
				}

				switch (choice) {
				case '1':
					a.moveBoat(player2, player1);
					break;
				case '2':
					coord = a.chooseCoordinate(player2);
					a.attack(player2, player1, coord);
					break;
				case '3':
					if (player2.checkTorpedo()) {
						coord = a.chooseCoordinate(player2);
						a.superTorpedo(player2, player1, coord);
					} else {
						System.out
								.println("Vous n'avez plus de super torpille");
						System.out.println("Vous perdez votre tour");
					}
					break;
				case '4':
					if (player2.checkMine()) {
						coord = a.chooseCoordinate(player2);
						a.mine(player2, player1, coord);
					} else {
						System.out.println("Vous n'avez plus de mine");
						System.out.println("Vous perdez votre tour");
					}
					break;
				case '5':
					coord = a.chooseCoordinate(player2);
					a.scan(player2, player1, coord);
					break;
				}
				Thread.sleep(2500);
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
			}
		}

		// annonce du vainqueur
		if (player1.getFleetSize() == 0) {
			System.out.println("Victoire de " + player2.getPlayerName());
		} else {
			System.out.println("Victoire de " + player1.getPlayerName());
		}
	}

}