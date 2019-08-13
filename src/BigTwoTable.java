import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class BigTwoTable implements CardGameTable {

	/*
	 * A card game associates with this table. 
	 */
	private BigTwoClient game;
	
	/*
	 * A boolean array indicating which cards are being selected.
	 */
	private boolean[] selected = new boolean[13];
	
	/*
	 * An integer specifying the index of the active player.
	 */
	private int activePlayer;
	
	/*
	 * The main window of the application.
	 */
	private JFrame frame;
	
	/*
	 * A panel for showing the cards of each player and the cards played on the table.
	 */
	private JPanel bigTwoPanel;
	/*
	 * A "Play" button for the active player to play the selected cards.
	 */
	private JButton playButton;
	
	/*
	 * A "Pass" button for the active player to pass his/her turn to the next player.
	 */
	private JButton passButton;
	
	/*
	 * A text area for showing the current game status as well as end of game messages.
	 */
	private JTextArea msgArea;
	
	/*
	 * A 2D array storing the images for the faces of the cards.
	 */
	private Image[][] cardImages = new Image[4][13];
	
	/*
	 * An image for the backs of the cards.
	 */
	private Image cardBackImage;
	
	/*
	 * An array storing the images for the avatars
	 */
	private Image[] avatars = new Image[4];
	
	/*
	 * A panel for button.
	 */
	private JPanel buttonPanel;
	
	/*
	 * A menu bar for the frame.
	 */
	private JMenuBar menuBar ;
	
	/*
	 * Panel consist of msgArea
	 */
	private JPanel leftPanel;
	
	/*
	 * A "reset" button for the active player to reset the card choice and enable the game
	 */
	private JButton resetButton;
	/*
	 * A "disable" Button for the active player to disable the move in game
	 */
	private JButton disableButton;
	/*
	 * A "clear" Button for the active player to clear the msgArea
	 */
	private JButton clearButton;
	
	/*
	 * Panel consist of the button on the left hand side
	 */
	private JPanel leftButtonPanel;
	
	/*
	 * A text area for showing the chat of players.
	 */
	private JTextArea showchatArea;
	
	/*
	 * A text area for typing the chat of players.
	 */
	private JTextField sendchatArea;
	
	/*
	 * Panel consist of the button on the bottom and the sendchatArea.
	 */
	
	private JPanel bottomPanel;
	
	/*
	 * A "send" Button for the player to send msg in sendchatArea
	 */
	private JButton sendButton;
	
	/*
	 * Panel consist of the "send" button and the sendchatArea.
	 */
	private JPanel chatPanel;
	
	/*
	 * Label of players' name 
	 */
	private JLabel [] PlayerLabel = new JLabel[4];
	
	/*
	 * A constructor for creating a BigTwoTable. The parameter game is a reference to a card game associates with this table.
	 */
	public BigTwoTable(BigTwoClient game) {
		this.game=game;
		
		frame = new JFrame("BigTwoGame");
		frame.setSize(1200,1000);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		bigTwoPanel = new BigTwoPanel();
		frame.add(bigTwoPanel, BorderLayout.CENTER);
		
		bottomPanel =new JPanel();//(new BorderLayout());
		frame.add(bottomPanel,BorderLayout.SOUTH);
		
		leftPanel = new JPanel(new BorderLayout());
		frame.add(leftPanel,BorderLayout.EAST);
		
		buttonPanel = new JPanel();
		bottomPanel.add(buttonPanel,BorderLayout.CENTER);
		
		chatPanel = new JPanel();
		bottomPanel.add(chatPanel,BorderLayout.EAST);
		
		leftButtonPanel= new JPanel();
		leftPanel.add(leftButtonPanel,BorderLayout.NORTH);
		
		playButton = new JButton("Play");
		playButton.addActionListener(new PlayButtonListener());
		buttonPanel.add(playButton);
		
		passButton = new JButton("Pass");
		passButton.addActionListener(new PassButtonListener());
		buttonPanel.add(passButton);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ResetButtonListener());
		leftButtonPanel.add(resetButton,BorderLayout.NORTH);
		
		disableButton = new JButton("Disable");
		disableButton.addActionListener(new DisableButtonListener());
		leftButtonPanel.add(disableButton,BorderLayout.NORTH);
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearButtonListener());
		leftButtonPanel.add(clearButton,BorderLayout.NORTH);
		
		msgArea= new JTextArea();
		msgArea.setLineWrap(true);
		msgArea.setForeground(Color.black);
		msgArea.setBackground(Color.WHITE);
		msgArea.setEditable(false);
		msgArea.setFont(new Font("Serif", Font.PLAIN, 20));
		msgArea.setPreferredSize(new Dimension(frame.getWidth()*1/3,frame.getHeight()/2));
		JScrollPane jsp = new JScrollPane(msgArea);
		leftPanel.add(jsp, BorderLayout.CENTER);
		
		showchatArea= new JTextArea();
		showchatArea.setLineWrap(true);
		showchatArea.setForeground(Color.black);
		showchatArea.setBackground(Color.WHITE);
		showchatArea.setEditable(false);
		showchatArea.setFont(new Font("Serif", Font.PLAIN, 20));
		showchatArea.setPreferredSize(new Dimension(frame.getWidth()*1/3,frame.getHeight()/2));
		JScrollPane jsp2 = new JScrollPane(showchatArea);
		leftPanel.add(jsp2, BorderLayout.SOUTH);
		
		sendchatArea= new SendChatArea();
		sendchatArea.setForeground(Color.black);
		sendchatArea.setBackground(Color.WHITE);
		sendchatArea.setFont(new Font("Serif", Font.PLAIN, 20));
		sendchatArea.setPreferredSize(new Dimension(frame.getWidth()*1/3-75,25));
		chatPanel.add(sendchatArea, BorderLayout.WEST);
		
		sendButton=new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		chatPanel.add(sendButton,BorderLayout.EAST);
		
		menuBar =new JMenuBar();
		JMenu menu =new JMenu("BigTwo");
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new QuitMenuItemListener());
		JMenuItem restart = new JMenuItem("Restart");
		restart.addActionListener(new RestartMenuItemListener());
		menu.add(restart);
		menu.add(quit);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
	
		frame.setVisible(true);
		
	}
	

	@Override
	public void setActivePlayer(int activePlayer) {
		if (activePlayer < 0 || activePlayer >= game.getPlayerList().size()) {
			this.activePlayer = -1;
		} else {
			this.activePlayer = activePlayer;
		}
	}

	@Override
	public int[] getSelected() {
		int[] cardIdx = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count++;
			}
		}

		if (count != 0) {
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					cardIdx[count] = j;
					count++;
				}
			}
		}
		return cardIdx;
	}

	@Override
	public void resetSelected() {
		selected = new boolean[13];
		
	}

	@Override
	public void repaint() {
		bigTwoPanel.repaint();
		
	}

	@Override
	public void printMsg(String msg) {
		this.msgArea.append(msg);
		
	}

	@Override
	public void clearMsgArea() {
		this.msgArea.setText(null);
		
	}

	@Override
	public void reset() {
		this.repaint();
		enable();
	}

	@Override
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
	}

	@Override
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
		
	}
	
	public void restart() {
		clearMsgArea();
		cardImages = new Image[4][13];
		BigTwoDeck btDeck = new BigTwoDeck();
		btDeck.shuffle();
		game.start(btDeck);
		repaint();
	}
	
	public void printChatMsg(String msg) {
		this.showchatArea.append(msg);
		
	}
	
	public void updateName() {
		for (int i=0; i<4;i++ ) {
			PlayerLabel[i].setText(game.getPlayerList().get(i).getName());
		}
	}
	
	public void iniCard() {
		((BigTwoPanel) bigTwoPanel).iniLocation();
	}
	
	class BigTwoPanel extends JPanel implements MouseListener {
		
		/*
		 * A panel consists of the cards played 
		 */
		private JPanel tablePanel;
		private boolean isInsert =false;
		private int cardWidth=73;
		private int cardHeight=97;
	

		private JLabel LastCardsPlayed;
		private JLabel LastPlayedPlayer;
		
		
		private int xIni[] = {150,150,150,150} ; //initial x
		private int yIni[] = {100, 100+(cardHeight+55)*1,100+(cardHeight+55)*2,100+(cardHeight+55)*3} ; // initial y
		
		private int[][] imagesX=new int [4][13];
		private int[][] imagesY=new int [4][13];
		
		private final char[] SUITS = {'d','c','h','s'};
		private final char[] RANKS = {'a','2','3','4','5','6','7','8','9','t','j','q','k'};
		
		public BigTwoPanel() {
			setLayout(null);
			this.setBackground(Color.green);
			
			
			PlayerLabel[0]=new JLabel(game.getPlayerList().get(0).getName());
			PlayerLabel[0].setFont(new Font("Serif", Font.PLAIN, 20));
			PlayerLabel[0].setBounds(0,yIni[0]-40,120,25);
			
			PlayerLabel[1]=new JLabel(game.getPlayerList().get(1).getName());
			PlayerLabel[1].setFont(new Font("Serif", Font.PLAIN, 20));
			PlayerLabel[1].setBounds(0,yIni[1]-40,120,25);
			
			PlayerLabel[2]=new JLabel(game.getPlayerList().get(2).getName());
			PlayerLabel[2].setFont(new Font("Serif", Font.PLAIN, 20));
			PlayerLabel[2].setBounds(0,yIni[2]-40,120,25);
			
			PlayerLabel[3]=new JLabel(game.getPlayerList().get(3).getName());
			PlayerLabel[3].setFont(new Font("Serif", Font.PLAIN, 20));
			PlayerLabel[3].setBounds(0,yIni[3]-40,120,25);
			
			LastCardsPlayed= new JLabel ("Last card(s) on the table: (played by)");
			LastCardsPlayed.setFont(new Font("Serif", Font.PLAIN, 20));
			LastCardsPlayed.setBounds(0,700,300,25);
			
			LastPlayedPlayer =new JLabel();
			LastPlayedPlayer.setFont(new Font("Serif", Font.PLAIN, 20));
			LastPlayedPlayer.setBounds(0,720,120,25);
			
			this.add(PlayerLabel[0]);
			this.add(PlayerLabel[1]);
			this.add(PlayerLabel[2]);
			this.add(PlayerLabel[3]);
			this.add(LastCardsPlayed);
		
			addMouseListener(this);
		}
		
		public boolean isInsert() {
			return this.isInsert;
		}
		
		public void insertImage () {
			//insert avatar image
			for (int i=0 ; i<4;i++) {
				avatars[i]= new ImageIcon ("src/avatars/Player"+i+".jpg").getImage();
			}
			//insert card image 	
			for (int i=0 ; i<4;i++) {
				for (int j=0; j<13;j++) {
						cardImages[i][j]=new ImageIcon("src/cards/"+RANKS[j]+SUITS[i]+".gif").getImage();
				}
				//x=0;
				//y+=cardImages[i][0].getHeight(this);
			}
			cardBackImage = new ImageIcon("src/back.png").getImage();
		}
		
		public void cardsPaint(Graphics g) {
			for (int i=0; i<4;i++) {
				if (game.getPlayerID()==i) {
					for (int j=0; j<game.getPlayerList().get(i).getNumOfCards();j++) {
						Card card = game.getPlayerList().get(i).getCardsInHand().getCard(j);
						if (cardImages[card.getSuit()][card.getRank()]!=null) {
							g.drawImage(cardImages[card.getSuit()][card.getRank()], imagesX[card.getSuit()][card.getRank()], imagesY[card.getSuit()][card.getRank()], this);
						}else {
							System.out.println("null");
						}				
					}
				}else {
					for (int j=0; j<game.getPlayerList().get(i).getNumOfCards();j++) {
						Card card = game.getPlayerList().get(i).getCardsInHand().getCard(j);
						if (cardImages[card.getSuit()][card.getRank()]!=null) {
							g.drawImage(cardBackImage, imagesX[card.getSuit()][card.getRank()], imagesY[card.getSuit()][card.getRank()],cardWidth,cardHeight, this);
						}else {
							System.out.println("null");
						}				
					}
					
				}
			}
		}
		public void iniLocation() {
			for (int i=0; i<4;i++) {
				int x =xIni[i];
				if(game.getPlayerID()==i) {
					for (int j=0; j<game.getPlayerList().get(i).getNumOfCards();j++) {
						Card card = game.getPlayerList().get(i).getCardsInHand().getCard(j);
						imagesX[card.getSuit()][card.getRank()]=x;
						imagesY[card.getSuit()][card.getRank()]=yIni[i];
						x+=cardImages[card.getSuit()][card.getRank()].getWidth(this)-50;
					}
				}else {
					for (int j=0; j<game.getPlayerList().get(i).getNumOfCards();j++) {
						Card card = game.getPlayerList().get(i).getCardsInHand().getCard(j);
						imagesX[card.getSuit()][card.getRank()]=x;
						imagesY[card.getSuit()][card.getRank()]=yIni[i];
						x+=cardImages[card.getSuit()][card.getRank()].getWidth(this)-50;
					}
				}
			}
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(!isInsert) {
				this.insertImage ();
				isInsert=true;
			}
			//print avatars
			for (int i=0; i<4;i++) {
				if (avatars[i]!= null) {
					g.drawImage(avatars[i],0,yIni[i]-10, 120, cardHeight+2*10,this);
				}else {
					System.out.println("null");
				}
			}
			//print card images
			cardsPaint(g);
			
			//print hand on table 
			if (game.getHandsOnTable().size()>1) {
				int x = 150;
				LastPlayedPlayer.setText(game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer().getName());
				this.add(LastPlayedPlayer);
				g.drawImage(avatars[game.getPlayerList().indexOf(game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer())],0,750,120, cardHeight+2*10,this);
				for (int i =0; i<game.getHandsOnTable().get(game.getHandsOnTable().size()-1).size();i++) {
					Card card = game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getCard(i);
					g.drawImage(cardImages[card.getSuit()][card.getRank()], x, 750,cardWidth,cardHeight, this);
					x=x+cardWidth+10;
				}
			}
			
			if (game.getPlayerID()!=game.getCurrentIdx()) {
				disable();
			}
			
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
				if (game.getPlayerID()==game.getCurrentIdx()) {
					for (int j = game.getPlayerList().get(game.getCurrentIdx()).getNumOfCards()-1; j>-1; j--) {
						Card card = game.getPlayerList().get(game.getPlayerID()).getCardsInHand().getCard(j);
						if(arg0.getX()>=imagesX[card.getSuit()][card.getRank()] && arg0.getX()<= imagesX[card.getSuit()][card.getRank()]+cardWidth && arg0.getY()>= imagesY[card.getSuit()][card.getRank()]  && arg0.getY() <= imagesY[card.getSuit()][card.getRank()]+cardHeight) {
							if (imagesY[card.getSuit()][card.getRank()]==yIni[game.getPlayerID()])
								imagesY[card.getSuit()][card.getRank()]-=10 ;
							else
								imagesY[card.getSuit()][card.getRank()]+=10 ;
							break;
						}
						
					}	
				}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		/*
		class PlayerPanel extends JPanel implements MouseListener{
			private JLabel name;
			
			public PlayerPanel (int n) {
				
				name=new JLabel(game.getPlayerList().get(n).getName());
				this.add(name);
			}
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		}
	
		*/

		public int[][] getImagesY() {
			return imagesY;
		}

		public int[] getYIni() {
			return yIni;
		}
	}
	
	class SendChatArea extends JTextField implements ActionListener{
		public SendChatArea() {
			addActionListener(this);
		}
		public void actionPerformed(ActionEvent e) {
			game.sendMessage(new CardGameMessage(CardGameMessage.MSG,game.getPlayerID(),this.getText()));
			this.setText(null);
		}
		
	}

	class PlayButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int selectedno = 0;
				for (int j = 0 ; j<game.getPlayerList().get(game.getCurrentIdx()).getNumOfCards() ; j++) {
					Card card = game.getPlayerList().get(game.getCurrentIdx()).getCardsInHand().getCard(j);
					if (((BigTwoPanel) bigTwoPanel).getImagesY()[card.getSuit()][card.getRank()]!= ((BigTwoPanel) bigTwoPanel).getYIni()[game.getCurrentIdx()]) {
						selected[j]=true;
						selectedno++;
					}
				}
		
			if (selectedno >0) {
				game.makeMove(game.getCurrentIdx(), getSelected());
				((BigTwoPanel) bigTwoPanel).insertImage ();
				resetSelected();
			}
		}
		
	}
	
	class PassButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (int j = 0 ; j<13 ; j++) {
				selected[j]=false;
			}
			
			game.makeMove(game.getCurrentIdx(), getSelected());
		}
		
	}
	
	public class ResetButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			reset();
			printMsg(game.getPlayerList().get(activePlayer).getName()+"'s turn:\n");

		}

	}
	
	public class DisableButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			disable();

		}

	}
	
	public class ClearButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			clearMsgArea();
		}

	}
	
	public class SendButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			game.sendMessage(new CardGameMessage(CardGameMessage.MSG,game.getPlayerID(),sendchatArea.getText()));
			sendchatArea.setText(null);
		}



	}
	
	class RestartMenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			restart() ;
		}
		
	}
	
	class QuitMenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
			
		}
		
	}


}
