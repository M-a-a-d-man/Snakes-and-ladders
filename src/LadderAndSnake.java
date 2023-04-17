import java.util.Scanner;
/**
 * 
 * 
 * -----------------------------------------------------<br>
 * Assignment 0 <br>
 * COMP249 <br>
 * Question: Part 1 <br>
 * Written by: Evan Teboul, 40238390 <br>
 * Due date: February 3 2023 <br>
 * -----------------------------------------------------<br>
 * 
 * Description
 * This class contains the main methods for setting up the Snakes and Ladder game board, and contains the main game mechanics.
 * This program allows you to play snakes and ladders.
 * 
 * This game has many rules which get explained in the methods below.
 * 
 * The first person to get to square 100 wins the game, however this will be a challenge since the player must avoid the snake traps
 * that will stop them from getting to square 100. But the players can use ladders to get there.
 * 
 * @author Evan Teboul, 40238390
 *
 */
public class LadderAndSnake {
	
	private Player[][] board = new Player[10][10]; //10x10 board with a number of players attached to each.
	private int nbOfplayers; // this will keep track of the number players playing the game.

	/**
	 * 
	 * @param nbOfplayers number of players that will be playing the game.
	 * 
	 */
	public LadderAndSnake(int nbOfplayers) {
		this.nbOfplayers = nbOfplayers;
	}
	/**
	 * gets the amount of players that will play the game.
	 * @return integer is returned that returns amount of players that will be playing
	 */
	public int getNbOfplayers() {
		return this.nbOfplayers;
	}
	/**
	 * this method will make sure if the user entered the write amount of players to play the game.
	 */
	public void playerNbChecker() { //checks number of players
		
		if(this.getNbOfplayers() > 2) {// if a number of players is not 2 then the program terminates
			System.out.println("Initialization was attempted for " + this.getNbOfplayers() + " member of players; however, this is only"
					+ " expected for an extended version the game.\nValue will be set to 2\n");
			this.setNbOfplayers(2);
			// the number assigned will then be stored in the board.	
		}
			
		if(this.getNbOfplayers()<2) {
			System.out.println("Error: Cannot execute the game with less than 2 players! Will exit");
			System.exit(0);
		}
		
	}
	/**
	 * This method will set the amount of players playing the Snakes and Ladders game.
	 * @param nbOfplayers this integer represents the amount of players that will play the game
	 */
	public void setNbOfplayers(int nbOfplayers) {
		this.nbOfplayers = nbOfplayers;
	}
	/**
	 * places the player into a square on the board, the two digit number corresponds to a specific array column and number
	 * uses the player's row and column attribute to locate where to put the player onto the board.
	 * @param player is an object of class Player
	 * 
	 * */
	public void setBoard(Player player) {
		
		board[player.getRow()][player.getColumn()] = player;
	} 
	/**
	 * this method will set the current array element to null, essentially clearing the square.
	 * @param player is an Object of class Player
	 * 
	 */
	public void resetSquare(Player player) {
		this.board[player.getRow()][player.getColumn()] = null;
	}
	/**
	 * this method simulates a dice flip, by generating a number from 1-6
	 * @return value is the integer number that represents the dice roll.
	 * 
	 */
	public static int flipDice() {
		int value = (int)(Math.floor(Math.random() * 6) + 1); //add one since we never want to get 0 and we want to get 6
		return value;
	} 
	/**
	 * The reason for this method is because position and its location in the array kept getting mismatched.
	 * this method ensures that the player position attribute matches up with .
	 * 
	 * Uses position to set the row coordinates.
	 * @param player is an object of class Player
	 * 
	 */
	public void positionArray(Player player) { //makes sure that the position and player location always match up.
		
		
		if(player.getPosition() == 100) { //did this to avoid row being set to 10
			player.setRow(9); //assigns the player to the equivalent row in the array.
			player.setColumn(9); //assigns the player to a specific row			
		}
		else if(player.getPosition()%10 == 0){ // for all numbers divible by 10, the column 
			player.setRow((int)(Math.floor((player.getPosition())/10.0))); //assigns the player to the equivalent row in the array.
			player.setColumn((int)(player.getPosition()/10)-1);
		}
		else {
			player.setRow((int)(Math.floor((player.getPosition())/10.0))); //assigns the player to the equivalent row in the array.
			player.setColumn((player.getPosition()%10) -1);
		}
	}
	
	/**
	 * Special condition:
	 * If a player rolls and lands a square that is already occupied by another player then the player who occupied the square first
	 * is kicked off and is sent off the board and must restart the game.
	 * 
	 * So the player at that position first gets their position attributes reset to zero.
	 * then they get moved, which is done by the setBoard method
	 * 
	 * special condition is checked before the board is set.
	 * 
	 * @param player is an object of class Player
	 * 
	 */
	public void specialCondition(Player player) {
		
		if(board[player.getRow()][player.getColumn()] != null) { 
			board[player.getRow()][player.getColumn()].setRow(0);
			board[player.getRow()][player.getColumn()].setColumn(0);
			board[player.getRow()][player.getColumn()].setPosition(0); // player attributes get set to 0
			this.setBoard(board[player.getRow()][player.getColumn()]); // player gets moved to 0.
			System.out.println(board[player.getRow()][player.getColumn()].getName() + " must restart from off the board.");
		}
	}
	
	
	
	/**
	 * 
	 * this changes the position, but now we need to connect this position to a place on the board
     * I used a parameter to be able to make this method place the player on a square on the selected game.	
	 * this method will move a player. each player object will use this method each turn.
	 * To use this method, place the LadderAndSnake object in the parameter and use it on a specific player.
	 * 
	 * This method flips the dice and advances the player by the value that they received on the dice
	 * if a player has already had their first turn and is on the board, then when they move, the reset square method activates to clear the square that it currently is in.
	 * The reason for this is because we do not multiple version of a single player in different squares, so we need to clear the old version of that player, with the old position and information.
	 * If the player starts from off the board i.e. position 0, then the reset method is skipped.
	 * Then we have "positionArray()" which is used to correctly place a player into a square so that they are in the correct square on the board and in the array.
	 * 
	 * Then we have the movement restrictions:
	 * 1. if a player rolls the dice and exceeds square 100, they move back by the excess amount.
	 * 2. if more than one player is a square the player who got there last kicks out the other player and sets them off the board.
	 * 
	 * Once all the restrictions are checked then the player is moved with the setBoard method.
	 * 
	 * 
	 * @param player is an object of class Player
	 * 
	 */
	public void move(Player player) { 
			
		Player temp = player; //this will be used to reset the square.
		int moveBy = flipDice(); //flips the dice
		
		if(player.getPosition() > 0)
			this.resetSquare(temp); //this resets the square the current square back to null so that another player can jump to it.
		player.setPosition(player.getPosition()+ moveBy); //sets square number on board
		if(player.getPosition()>100) { // if player position exceeds 100 then they move back by the excessive amount.
			int back = player.getPosition()-100;
			player.setPosition(100); // player reaches 100
			player.setPosition(player.getPosition()-back); //the excessive amounts set it back 
			positionArray(player); //sets row and column.
		}
		System.out.println(player.getName() + " got a dice value of " + moveBy + " now in square " + player.getPosition()); //special condition; multiple players in one square.
		LadderAndSnake.ladders(player);
		LadderAndSnake.Snake(player);
		positionArray(player); //sets column and row.
		
		
		this.specialCondition(player); //checks special condition, this can be done last since, the person who is moving kicks off the person occupying the square.
		
		this.setBoard(player); //takes the row and column of the player and assigns the player to that array location on the board.
		
	} 
	
	/**
	 * This changes the player's position attribute before the position vector is translated to array coordinates.
	 * if a player lands on the bottom of a ladder they climb up the ladder and advance on the board.
	 * @param player is an object of class Player
	 */
	
	public static void ladders(Player player) {
		if(player.getPosition() == 1) {
			int temp = player.getPosition();
			player.setPosition(38); //sends to square 38
			System.out.println( player.getName() + " landed on a ladder at square " + temp + " and climbs to square " + player.getPosition());
			
		}
		if(player.getPosition() == 4) {
			int temp = player.getPosition();
			player.setPosition(14);
			System.out.println( player.getName() + " landed on a ladder at square " + temp + " and climbs to square " + player.getPosition());
		}
		if(player.getPosition() ==9) {
			int temp = player.getPosition();
			player.setPosition(31);
			System.out.println( player.getName() + " landed on a ladder at square " + temp + " and climbs to square " + player.getPosition());
		}
		if(player.getPosition() == 21) {
			int temp = player.getPosition();
			player.setPosition(42);
			System.out.println( player.getName() + " landed on a ladder at square " + temp + " and climbs to square " + player.getPosition());
		}
		if(player.getPosition() ==28) {
			int temp = player.getPosition();
			player.setPosition(84);
			System.out.println( player.getName() + " landed on a ladder at square " + temp + " and climbs to square " + player.getPosition());
		}
		if(player.getPosition()==36) {
			int temp = player.getPosition();
			player.setPosition(44);
			System.out.println( player.getName() + " landed on a ladder at square " + temp + " and climbs to square " + player.getPosition());
		}
		
		if(player.getPosition() == 51) {
			int temp = player.getPosition();
			player.setPosition(67);
			System.out.println( player.getName() + " landed on a ladder at square " + temp + " and climbs to square " + player.getPosition());
		}
		
		if(player.getPosition() == 71) {
			int temp = player.getPosition();
			player.setPosition(91);
			System.out.println( player.getName() + " landed on a ladder at square " + temp + " and climbs to square " + player.getPosition());
		}
		if(player.getPosition() == 80) {
			int temp = player.getPosition();
			player.setPosition(100);
			System.out.println( player.getName() + " landed on a ladder at square " + temp + " and climbs to square " + player.getPosition());
		}
	}
	
	/**
	 * if a player lands on a square that has the head of a snake on it then they slide all the way down to a lower tile/square.
	 * @param player is an object of class Player
	 * 
	 */
	public static void Snake(Player player) {
		if(player.getPosition() == 16) {
			int temp = player.getPosition();
			player.setPosition(6);
			System.out.println("Oh no, " + player.getName()+ " landed on a snake's head at square " + temp + " and slides down to square " + player.getPosition());
		}
		if(player.getPosition() == 48) {
			int temp = player.getPosition();
			player.setPosition(30);
			System.out.println("Oh no, " + player.getName()+ " landed on a snake's head at square " + temp + " and slides down to square " + player.getPosition());
		}
		if(player.getPosition() == 64) {
			int temp = player.getPosition();
			player.setPosition(60);
			System.out.println("Oh no, " + player.getName()+ " landed on a snake's head at square " + temp + " and slides down to square " + player.getPosition());
		}
		if(player.getPosition() == 79) {
			int temp = player.getPosition();
			player.setPosition(19);
			System.out.println("Oh no, " + player.getName()+ " landed on a snake's head at square " + temp + " and slides down to square " + player.getPosition());
		}
		if(player.getPosition() == 93) {
			int temp = player.getPosition();
			player.setPosition(68);
			System.out.println("Oh no, " + player.getName()+ " landed on a snake's head at square " + temp + " and slides down to square " + player.getPosition());
		}
		if(player.getPosition() == 95) {
			int temp = player.getPosition();
			player.setPosition(24);
			System.out.println("Oh no, " + player.getName()+ " landed on a snake's head at square " + temp + " and slides down to square " + player.getPosition());
		}
		if(player.getPosition() == 97) {
			int temp = player.getPosition();
			player.setPosition(76);
			System.out.println("Oh no, " + player.getName()+ " landed on a snake's head at square " + temp + " and slides down to square " + player.getPosition());
		}
		if(player.getPosition() == 98) {
			int temp = player.getPosition();
			player.setPosition(78);
			System.out.println("Oh no, " + player.getName()+ " landed on a snake's head at square " + temp + " and slides down to square " + player.getPosition());
		}
	}
	
	
	/**
	 * This is a static method, which checks if a player has won, they win by reaching position 100.
	 * However, the position 100 in the array is at index [9][9], which means it is at the last index of the board array.
	 * This method checks both position and the array indexes to be sure that the player has won.
	 * The first if statement actually checks if a player has won, if not the method checks if the player in the parameter is player 2
	 * this is because if the first player has not won yet the second player can still win, so it only displays the "flipping again" message after both players
	 * have ended their turn.
	 * 
	 * @param player an object of class Player.
	 * 
	 */
	public void checkWinner(Player player) {
		if(player.getPosition() == 100 && player.getColumn()==9 && player.getRow()==9 && player.getplayerNb() == this.board[9][9].getplayerNb()) { 
			System.out.println("Game over! " + player.getName() + " Wins!");
			System.out.println("\nThank you for playing Snake and Ladders!");
			System.exit(0);
		}
		if(player.getplayerNb()==2)//the reason for adding this is because we will be using this method twice,																																									//and we do not want the flipping again message to be displayed twice
			System.out.println("Game not over; flipping again");
	}
	/** 
	 * the following method is what will use all the LadderAndSnake methods and player methods to play the game.
	 */
	
	public static void play() {
		Scanner keyIn = new Scanner(System.in);
		System.out.println("Welcome to my Snakes and Ladder game!\n");
		System.out.print("Please enter the number of players that will be playing: ");
		int nbOfPlayers = keyIn.nextInt();
		LadderAndSnake game = new LadderAndSnake(nbOfPlayers); //creates the board
		game.playerNbChecker();
		
		System.out.println("Do you wish to name each player?\nIf Yes enter \"1\"\nIf not enter \"0\"");
		int enterName = keyIn.nextInt();
		String p1 = "Player 1"; //sets a default name.
		String p2 = "Player 2"; //set default name
		
		if(enterName == 1) { //sets up name 
			System.out.print("Enter a name for Player 1 (no spaces): ");
			p1 = keyIn.next();
			System.out.print("Enter a name for Player 2 (no spaces): ");
			p2 = keyIn.next();
		}
		
		keyIn.close();  //after this point we will no longer use the keyboard.
		
		Player player1 = new Player(p1); //creates the first player object
		Player player2 = new Player(p2); // creates the second player object
		
		Player.whoStarts(player1, player2); //rolls and checks who starts the game.
		
		if(player1.getplayerNb() == 1) { //if player one starts then they make the first move, otherwise player 2 starts
			while(game.board[game.board.length -1][game.board.length-1] == null) {
				game.move(player1);	
				game.checkWinner(player1); //checks the starting player first then checks the second player
				
				game.move(player2);	//moves player 1
				game.checkWinner(player2); //checks the starting player first then checks the second player
				
				System.out.print("\n");
			}
		}
		
		else {
			while(game.board[game.board.length -1][game.board.length-1] == null) { //runs the game as long as no one has won
				game.move(player2); //moves player
				game.checkWinner(player2);
				
				game.move(player1);	//moves player 1
				game.checkWinner(player1); //method does the same thing as the above code
				
				System.out.print("\n");
			}
		}
		
	}
}