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
 *This class contains all the attributes of the player like the name of the player (name), their coordinates on the board (row and column), whether they start first or second (row,column)
 *and the number of the square they are in (position)
 *
 *@author Evan Teboul
 * 
 *
 */
public class Player {
	/**
	 * position is the number of the square on the board.
	 */
	private int position = 0;
	
	/**
	 * playerNb is the order of the player, i.e, if this = 1 then this player starts.
	 */
	private int playerNb = 0;
	
	/**
	 * this is the player's name
	 */
	private String name;
	/**
	 * this attribute will determine the array row index.
	 */
	private int row;
	
	/**
	 * This attribute will determine the array's column or vertical index. Meaning which column in the grid.
	 */
	private int column;
	
	public Player() {
		this.playerNb = 0;
		this.position = 0;
		this.name = "player";
		row = 0;
		column = 0;
	}
	
	public Player(Player player) {
		this.position = player.position;
		this.playerNb = player.playerNb;
		this.name = player.name;
		this.row = player.row;
		this.column = player.column;
	}
	
	public Player(String name) {
		this.playerNb = 0;
		this.position = 0;
		this.name = name;
		row = 0;
		column = 0;
	}
	/**
	 * gets player position
	 * @return returns the integer position whihc is essentially, the square that the person would be on.
	 */
	public int getPosition() {
		return position;
	}
	/**
	 * gets the playerNb.
	 * @return playerNb is the order number of the player i.e. player with playerNb = 1, starts the game
	 */
	public int getplayerNb() {
		return playerNb;
	}
	/**
	 * gets the player name
	 * @return returns the name of the player
	 * 
	 */
	public String getName() {
		return name;
	}
	/**
	 * gets player row, which means it will display what row in the board array the player is in
	 * @return returns the horizontal coordinate of the array
	 */
	public int getRow() {
		return row;
	}
	/**
	 * gets the player column, which means it will display what column in the board array the player is in
	 * @return returns the index of the array in the row.
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * The following will be used to set the player's square number that it is currently in.
	 * @param position 
	 */
	public void setPosition(int position) { 
		/**we set column and row here so that when a player changes position we also change the array values */
		this.position = position;
//		setRow((int)(Math.ceil((position-1)/10.0)) -1);
//		setColumn((int) position%10);
	}
	/**
	 * this method sets the row number, which is important for identifying the row in the board array.
	 * @param row this is the horizontal component on the board.
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * this method sets the row number, which is important for identifying the column in the board array.
	 * @param column this is the vertical coordinate on the board.
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/**
	 * sets the player number, which is used in the whoStarts method. player with "playerNb" == 1 will start.
	 * @param playerNb this is the of the player turns, i.e. playerNb = 1, is the player who starts first
	 */
	public void setPlayerNb(int playerNb) {
		this.playerNb = playerNb;
	}
	
	
	/**
	 * This method is static so it does not have to be used on any object, it just requires two inputs. 
	 * The players that are placed as a parameters will each take turns rolling, the person with a higher dice roll 
	 * will then be assigned a playerNb of 1, in its attribute, "playerNb", which will later be used to identify the player who starts the game.
	 * The player with the smaller dice roll will receive a player of 2, so they will start their turn after the other player.
	 * The number of attempts is also recorded and is reported once the starting player is determined.
	 * @param p1 this is a Player object
	 * @param p2 this is a Player object
	 * @return this method returns the Player object that will start the game, meaning the player that will start each turn and roll first.
	 */
	public static Player whoStarts(Player p1, Player p2) { //will be used once at the start of the game.
		int attempts = 1;
		// I placed the dice roll in the player number so that I would not need to make a new variable, for the dice value.
		// the player with the higher dice roll will have PlayerNb = 1, and the other will have playerNb 2.
		
		do {
			p1.setPlayerNb(LadderAndSnake.flipDice());
			p2.setPlayerNb(LadderAndSnake.flipDice());
			
			System.out.println(p1.getName() +" got a dice value of " + p1.getplayerNb());
			System.out.println(p2.getName() + " got a dice value of " + p2.getplayerNb());
			
			if(p1.getplayerNb()>p2.getplayerNb()) {
				System.out.println("Reached final decision on order of player: "+ p1.getName() + " then " + p2.getName() +".\nIt took " +
			     attempts + " attempts before a decision could be made.\n");
				p1.setPlayerNb(1);  //smaller number should start
				p2.setPlayerNb(2);
			}	
			else if(p1.getplayerNb()<p2.getplayerNb()) {
				System.out.println("Reached final decision on order of player: "+ p2.getName() + " then " + p1.getName() +".\nIt took " +
					     attempts + " attempt(s) before a decision could be made.\n");
				p1.setPlayerNb(2);  //smaller number should start
				p2.setPlayerNb(1);
			}
			else
				System.out.println("A tie was achieved between Player 1 and Player 2. Attempting to break the tie");
		
			attempts++;
		}
		while(p1.getplayerNb() == p2.getplayerNb()); //runs again if they get the same roll.
		
		if(p1.getplayerNb()==1) // meaning that this player will be first
			return p1;
		else
			return p2;
	}
	
	public static void main(String args[]) {
	
	}
}
