import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class BigTwoClient implements CardGame, NetworkGame {
	
	/*
	 * An integer specifying the number of players.
	 */
	private int numOfPlayers;
	/*
	 * A deck of cards.
	 */
	private Deck deck;
	/*
	 * A list of players.
	 */
	private ArrayList<CardGamePlayer> playerList = new ArrayList<CardGamePlayer>();
	/*
	 * A list of hands played on the table.
	 */
	private ArrayList<Hand> handsOnTable = new ArrayList<Hand>();
	
	/*
	 * An integer specifying the playerID(i.e.index) of the local player.
	 */
	private int playerID;
	/*
	 * A string specifying the name of the local player.
	 */
	private String playerName;
	/*
	 * A String specifying the IP address of the game server.
	 */
	private String serverIP;
	/*
	 * An integer specifying the TCP port of the game server.
	 */
	private int serverPort;
	/*
	 * A socket connection to the game server.
	 */
	private Socket sock;
	/*
	 * An ObjectOutputStream for sending messages to the server.
	 */
	private ObjectOutputStream oos;
	/*
	 * An integer specifying the index of the current player.
	 */
	private int currentIdx;
	/*
	 * A Big Two table which builds the GUI for the game and handles all user actions.
	 */
	private BigTwoTable table;
	
	
	/*
	 * 
	 * Creates and returns an instance of the Big Two class.
	 * 
	 */
	public BigTwoClient() {
		CardGamePlayer P1 = new CardGamePlayer();
		CardGamePlayer P2 = new CardGamePlayer();
		CardGamePlayer P3 = new CardGamePlayer();
		CardGamePlayer P4 = new CardGamePlayer();
		playerList.add(P1);
		playerList.add(P2);
		playerList.add(P3);
		playerList.add(P4);
		table= new BigTwoTable(this);
		playerName=JOptionPane.showInputDialog("Tpye in your name:");
		makeConnection();
		
	}
	/*
	 * It is the main method to start a Big Two game
	 */
	public static void main (String [] args) {
		BigTwoClient BT = new BigTwoClient();
	}

	@Override
	public int getNumOfPlayers() {
		return playerList.size();
	}

	@Override
	public Deck getDeck() {
		return deck;
	}

	@Override
	public ArrayList<CardGamePlayer> getPlayerList() {
		return playerList;
	}

	@Override
	public ArrayList<Hand> getHandsOnTable() {
		return handsOnTable;
	}

	@Override
	public int getCurrentIdx() {
		return currentIdx;
	}

	@Override
	public void start(Deck deck) {
		this.deck=deck;
		for (int i =0 ; i< this.playerList.size();i++) { //loop for players
			this.getPlayerList().get(i).removeAllCards();
			for (int j =0 ; j<13; j++) { //loop for big two cards
				this.getPlayerList().get(i).addCard(deck.getCard(13*i+j));
			}
			this.getPlayerList().get(i).getCardsInHand().sort();
			
			if (this.getPlayerList().get(i).getCardsInHand().contains(new Card (0,2))){ // set active player with diamond 3 
				currentIdx=i;
				this.table.setActivePlayer(i);
			}
		}

		CardGamePlayer activePlayer = this.getPlayerList().get(this.getCurrentIdx()); // get current active player 
		this.table.printMsg(activePlayer.getName()+"'s turn:\n");
		handsOnTable.add(null);
		table.iniCard();
		table.repaint();
	}

	@Override
	public void makeMove(int playerID, int[] cardIdx) {
			sendMessage(new CardGameMessage(CardGameMessage.MOVE,playerID,cardIdx));
	}

	@Override
	public void checkMove(int playerID, int[] cardIdx) {
		CardGamePlayer activePlayer = this.getPlayerList().get(this.getCurrentIdx()); // get current active player
		CardList playedCards = activePlayer.play(cardIdx); // get played card from cardIdx
		Hand playedHand = composeHand(activePlayer,playedCards); // compose as hand 
		int[] selected = cardIdx;
		
		System.out.println(playedCards);
		
		if (selected == null && handsOnTable.get(handsOnTable.size()-1)==null || (selected == null && handsOnTable.size()>1 && handsOnTable.get(handsOnTable.size()-1).getPlayer().equals(activePlayer))) {
			System.out.println("Not a legal move");
			this.table.printMsg("Not a legal move\n");
			
		}else if (selected == null) {
			this.table.printMsg("{Pass}\n");
			currentIdx=(currentIdx+1)%4;
			table.setActivePlayer(currentIdx);
			this.table.printMsg(this.getPlayerList().get(this.getCurrentIdx()).getName()+"'s turn:\n");
			table.iniCard();
			this.table.repaint();
		}
		//fail input, need input again 
		else if(playedHand == null ||
				(handsOnTable.size()==1 && !playedHand.contains(new Card (0,2)))||
				!playedHand.beats(handsOnTable.get(handsOnTable.size()-1))
				){ 
	
			this.table.printMsg(playedCards.toString()+"<=Not a legal move\n");
			table.iniCard();
			this.table.repaint();
			
			}else {
				if (playedHand != null) {	
					this.table.printMsg("{" + playedHand.getType() + "}"+playedHand.toString()+"\n" );
					handsOnTable.add(playedHand);
					activePlayer.removeCards(playedHand);
				}
				//next player turn
				currentIdx=(currentIdx+1)%4;
				table.setActivePlayer(currentIdx);
				this.table.printMsg(this.getPlayerList().get(this.getCurrentIdx()).getName()+"'s turn:\n");
				table.iniCard();
				this.table.repaint(); // update the table 
			}
		
		if (endOfGame()) {
			this.table.printMsg(this.getPlayerList().get(this.getCurrentIdx()).getName()+" wins");
			this.table.printMsg(this.getPlayerList().get((this.getCurrentIdx()+1)%4).getName()+" has "+this.getPlayerList().get((this.getCurrentIdx()+1)%4).getNumOfCards()+" cards left");
			this.table.printMsg(this.getPlayerList().get((this.getCurrentIdx()+2)%4).getName()+" has "+this.getPlayerList().get((this.getCurrentIdx()+2)%4).getNumOfCards()+" cards left");
			this.table.printMsg(this.getPlayerList().get((this.getCurrentIdx()+3)%4).getName()+" has "+this.getPlayerList().get((this.getCurrentIdx()+3)%4).getNumOfCards()+" cards left");
		}
	}

	@Override
	public boolean endOfGame() {
		if(this.getPlayerList().get(this.getCurrentIdx()).getCardsInHand().isEmpty()) {
			return true;
		}
		return false;
	}
	
// networkgame interface
	@Override
	public int getPlayerID() {
		return playerID;
	}

	@Override
	public void setPlayerID(int playerID) {
		this.playerID=playerID;
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName=playerName;
		
	}

	@Override
	public String getServerIP() {
		return serverIP;
	}

	@Override
	public void setServerIP(String serverIP) {
		this.serverIP=serverIP;
		
	}

	@Override
	public int getServerPort() {
		return serverPort;
	}

	@Override
	public void setServerPort(int serverPort) {
		this.serverPort=serverPort;
		
	}

	@Override
	public void makeConnection() {
		
		try {
			sock = new Socket("127.0.0.1", 2396);
			setServerIP("127.0.0.1");
			setServerPort(2396);
			oos = new ObjectOutputStream(sock.getOutputStream()); //create an OOS for sending message to game server
			System.out.println("networking established");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//create a thread for receiving messages
		Thread readerThread = new Thread(new ServerHandler());
		readerThread.start();
		//send a message of type JOIN to game server 
		sendMessage(new CardGameMessage(CardGameMessage.JOIN,-1,playerName));
		//send a message of type READY to server 
		sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
		
	}

	@Override
	public void parseMessage(GameMessage message) {
		switch (message.getType()) {
		
		case CardGameMessage.PLAYER_LIST:
			//set the playerID of local player
			setPlayerID(message.getPlayerID());
			// return player_list with names
			for (int i=0; i<((String[])message.getData()).length;i++) {
				System.out.println(((String[])message.getData())[i]);
				playerList.get(i).setName(((String[])message.getData())[i]);
			} 
			playerList.get(getPlayerID()).setName(playerName);
			table.updateName();
			break;
		case CardGameMessage.JOIN:
			// adds a player to the game
			playerList.get(message.getPlayerID()).setName((String) message.getData());
			table.updateName();
			break;
		case CardGameMessage.FULL:
			// display server is full
			table.printMsg("The server is full. Cannot join the game! Retry later.");
			break;
			
		case CardGameMessage.QUIT:
			// A player quita
			table.printMsg(playerList.get(message.getPlayerID()).getName()+" quits.\n");
			playerList.get(message.getPlayerID()).setName("");
			sendMessage(new CardGameMessage(CardGameMessage.READY,playerID,null));
			table.updateName();
			break;
			
		case CardGameMessage.READY:
			// marks the specified player as ready for a new game
			table.printMsg(playerList.get(message.getPlayerID()).getName()+" is ready!\n");
			break;
			
		case CardGameMessage.START:
			// Start the big two game
			table.printMsg("New Game Start!!\n");
			start((Deck)message.getData());
			
			break;
			
		case CardGameMessage.MOVE:
			checkMove(message.getPlayerID(),(int[]) message.getData());
			break;
		case CardGameMessage.MSG:
			table.printChatMsg((String)message.getData()+"\n");
			break;
			
		default:
			table.printMsg("Wrong message type: " + message.getType());
			// invalid message
			break;
		}
		
		
	}

	@Override
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		Hand hand;
		
		if (cards.size()==1) {
			hand = new Single(player, cards);
			if (hand.isValid()) {
				return hand;
			}
		}else if (cards.size()==2) {
			hand = new Pair(player, cards);
			if (hand.isValid()) {
				return hand;
			}
		}else if (cards.size()==3) {
			hand = new Triple(player, cards);
			if (hand.isValid()) {
				return hand;
			}
		}else if(cards.size()==5) {
			ArrayList<Hand> hand5List = new ArrayList<Hand>() ;
			Hand straight = new Straight(player, cards);
			Hand flush = new Flush(player, cards);
			Hand fullhouse = new FullHouse(player, cards);
			Hand quad = new Quad(player, cards);
			Hand straightflush = new StraightFlush(player, cards);
			
			hand5List.add(straightflush);
			hand5List.add(quad);
			hand5List.add(fullhouse);
			hand5List.add(flush);
			hand5List.add(straight);
			
			for (int i=0 ; i<hand5List.size()-1;i++) {
				if (hand5List.get(i).isValid()) {
					return hand5List.get(i);
				}else {
					continue;
				}
			}
		}
		return null;
		
	}
	/*
	 * A thread for handling the message from the server .
	 */
	class ServerHandler implements Runnable{
		public void run() {
			CardGameMessage CGM;
			try {
				ObjectInputStream ois =new ObjectInputStream(sock.getInputStream());
				while ((CGM =(CardGameMessage) ois.readObject())!= null) {
					parseMessage(CGM);
				}
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	

}
