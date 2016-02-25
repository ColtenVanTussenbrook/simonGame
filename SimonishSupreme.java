/**
 * Colten Van Tussenbrook
 * Chad Mano CS 2410 Java 
 * Simonish Supreme
 */

package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


//import components.ColorChooser;
import components.ColorPanelOrig;
import components.ScorePanelOrig;

/**
 * class for Simonish Supreme
 * extends JFrame, implements MouseListener + ActionListener
 * @author ColtenTuss
 */

public class SimonishSupreme extends JFrame implements MouseListener, ActionListener{
	
	public static Color color1 = Color.RED;
	public static Color color2 = Color.BLUE;
	public static Color color3 = Color.YELLOW;
	public static Color color4 = Color.GREEN;
	
	/**
	 * Everything for the frame + scores
	 */
	
	Color[] colorArr = {color1, color2, color3, color4};
	ColorPanelOrig[] panelArr = new ColorPanelOrig[4];
	ScorePanelOrig scorePanel;
	JPanel gamePanel;
	ArrayList<ColorPanelOrig> seq = new ArrayList<ColorPanelOrig>();
	Iterator seqItr;
	Random rand = new Random();
	boolean gameOver = true;
	int gamesPlayed;
	public ArrayList <Integer> gameScores = new ArrayList<Integer>();
	int averageScore;

	int opponentSeconds = 700;
	
	/**
	 * everything for the JMenu
	 */
	JMenuBar menuBar;
	JMenu settings;
	JMenu stats;
	JMenu help;
	JMenu chooseColor;
	JMenu chooseMode;
	JMenuItem highScore;
	JMenuItem history;
	JMenuItem about;
	JMenuItem rules;
	JMenu settings2;
	JMenuItem topLeft;
	JMenuItem topRight;
	JMenuItem bottomLeft;
	JMenuItem bottomRight;
	JMenuItem slow;
	JMenuItem medium;
	JMenuItem fast;
	
	public static Color panelColor = Color.white;
	
	Container pane;
		
	/**
	 * constructor - builds JFrame + game and creates the layout
	 */
	
	public SimonishSupreme() {
		this.setTitle("Simonish");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		
		pane = this.getContentPane();
		pane.setLayout(new BorderLayout());
		
		pane.setPreferredSize(new Dimension(400, 510));
		scorePanel = new ScorePanelOrig();
		scorePanel.setBounds(0, 0, 400, 50);
		scorePanel.getStartBtn().addActionListener(this);	
		
		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(2, 2));
		gamePanel.setBounds(0, 80, 400, 430);
		
		menuBar = new JMenuBar();  
		this.setJMenuBar(menuBar); //adds Menu Bar to frame
		menuBar.setBounds(0, 50, 400, 30);
		
		settings = new JMenu("Settings"); //settings tab
		menuBar.add(settings);
		chooseColor = new JMenu("Choose Color");
		chooseMode = new JMenu("Choose Mode");
		settings.add(chooseColor);
		settings.add(chooseMode);
		
		topLeft = new JMenuItem("Top Left");
		topRight = new JMenuItem("Top Right");
		bottomLeft = new JMenuItem("Bottom Left");
		bottomRight = new JMenuItem("Bottom Right");
		topLeft.addActionListener(this);
		topRight.addActionListener(this);
		bottomLeft.addActionListener(this);
		bottomRight.addActionListener(this);
		
		slow = new JMenuItem("Slow");
		medium = new JMenuItem("Medium");
		fast = new JMenuItem("Fast");
		slow.addActionListener(this);
		medium.addActionListener(this);
		fast.addActionListener(this);
		
		chooseColor.add(topLeft);
		chooseColor.add(topRight);
		chooseColor.add(bottomLeft);
		chooseColor.add(bottomRight);
		
		chooseMode.add(slow);
		chooseMode.add(medium);
		chooseMode.add(fast);
		
	
		stats = new JMenu("Stats"); //stats tab
		menuBar.add(stats);
		highScore = new JMenuItem("High Score");
		history = new JMenuItem("History");
		stats.add(highScore);
		stats.add(history);
		
		highScore.addActionListener(this);
		history.addActionListener(this);
		
		help = new JMenu("Help"); //help tab
		menuBar.add(help);
		about = new JMenuItem("About");
		rules = new JMenuItem("Rules");
		help.add(about);
		help.add(rules);
		
		about.addActionListener(this);
		rules.addActionListener(this);
		
		pane.add(scorePanel, BorderLayout.NORTH); //sets score panel in a border layout at the top
		pane.add(gamePanel, BorderLayout.CENTER); //sets game panel in a border layout in the center
		
		for (int i = 0; i < panelArr.length; i++) {
			panelArr[i] = new ColorPanelOrig(colorArr[i]);
			panelArr[i].addMouseListener(this);
			gamePanel.add(panelArr[i]);
		}	
				
		this.pack();
		this.setLocationRelativeTo(null);	
		this.setVisible(true);
		
		scorePanel.getPreviousScores(); //gets previous scores from previousScores.txt file
	}
	
	/**
	 * thread class for computer turn
	 */
	
	class threadComp implements Runnable
	{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		seq.add(panelArr[rand.nextInt(panelArr.length)]);
		
		for (ColorPanelOrig i : seq) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			i.pressed();
			try {
				Thread.sleep(opponentSeconds);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i.released();
		}
		
		seqItr = seq.iterator();
		}
	}
	
	/**
	 * game over method. ends game. displays dialog, asks for user name
	 */
	
	private void gameOver() 
	{
		gameOver = true;
		String msg = "Game Over";
//		if (scorePanel.getHighScore() == scorePanel.getScore()){
//			msg = msg + "\nYou got the high score!";
//		} not used currently
				
		JOptionPane.showMessageDialog(this, msg);
		scorePanel.highScores();
		JOptionPane.showMessageDialog(pane, scorePanel.getList());
		gameHistory();
		
		gameScores.add(scorePanel.getScore());
		
	}
	
	/**
	 * method for how many games have been played
	 * @return games played
	 */
	
	private int gameHistory()  //determines how many games have been played
	{
		return gamesPlayed;
	}
	
	/**
	 * method for finding the average score
	 * @return average score
	 */
	
	private int averageScore(){ //finds the average score

		if (!(gamesPlayed == 0))
		{
		int current = 0;
		for (int i = 0; i < gameScores.size(); i++)
		{
			current += gameScores.get(i); 
		}
		averageScore = current/gameScores.size();
		}
		return averageScore;
	}
	
	/**
	 * main method runs the Simonish program
	 * @param args
	 */
	
	public static void main(String[] args) {
		new SimonishSupreme();	
	}	
	
	/**
	 * action listener for user clicks. includes clicking on any JMenu item
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == topLeft) //sets background color for top left
		{
			color1 = JColorChooser.showDialog(this, "Choose color for top left", panelColor);
			panelArr[0].setBackground(color1);
		}
		if (src == topRight) //sets background color for top right
		{
			color2 = JColorChooser.showDialog(this, "Choose cholor for top right", panelColor);
			panelArr[1].setBackground(color2);
		}
		if (src == bottomLeft) //sets background color for bottom left
		{
			color3 = JColorChooser.showDialog(this, "Choose color for bottom left", panelColor);
			panelArr[2].setBackground(color3);
		}
		if (src == bottomRight) //sets background color for bottom right
		{
			color4 = JColorChooser.showDialog(this, "Choose color for bottom right", panelColor);
			panelArr[3].setBackground(color4);
		}
		
		if (src == slow) //sets game mode to slow
		{
			opponentSeconds = 1500;
		}
		if (src == medium) //sets game mode to medium
		{
			opponentSeconds = 700;
		}
		if (src == fast) //sets game mode to fast
		{
			opponentSeconds = 200;
		}
		
		if (src == highScore) //displays high score dialog box
		{
			
			JOptionPane.showMessageDialog(pane, scorePanel.getList()); 
		}
		
		if (src == history) //displays history dialog box. shows average score as well
		{
			JOptionPane.showMessageDialog(pane, "Number of Games Played: " + gameHistory() + "\nAverage Score: " + averageScore()); //need to add input
		}
		
		if (src == about) //displays about dialog box
		{
			JOptionPane.showMessageDialog(pane, "This is Simonish Supreme. Based on the popular family favorite handheld game Simon, Simonish Supreme takes this original idea a step further. \nIn this version, I've added additional game modes (slow, medium, fast), as well as an option to change colors. \nI've also added a high score menu to see if you crack the top ten, and a history menu to see how many games you've played and the average score. \n\nHave fun!!!");
		}
		
		if (src == rules) //displays rules dialog box
		{
			JOptionPane.showMessageDialog(pane, "Rules: \nClick start to begin a sequence. \nYou will see one square light up. After you see the square light up, click on the same square. \nEach time you click, the computer will add one to the sequence. \nThe idea is to follow the EXACT sequence of the computer. \nIf you click a square out of sequence, you lose. \nThe game will keep track of your score as you go along. \n\nGood luck!!!");
		}

		if (src == scorePanel.getStartBtn()) //starts new game if clicked
		{		
			gameOver = false;
			for (ColorPanelOrig i : panelArr) {
				i.changeBackground();
			}
			scorePanel.reset(); //resets the score panel
			seq.clear();
			Thread computerTurn = new Thread(new threadComp()); //thread for computer turn
			computerTurn.run();
			gamesPlayed++; //keeps track of how many games have been played
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mousePressed(MouseEvent e)  //what happens when the user presses their mouse
	{
		if (gameOver) return;
		
		ColorPanelOrig tmp = ((ColorPanelOrig)e.getSource());
		tmp.pressed();
		
		if (tmp != seqItr.next()) {
			gameOver();
		}		
	}

	@Override
	public void mouseReleased(MouseEvent e) //what happens when the user releases the mouse click
	{
		if (gameOver) return;
		
		ColorPanelOrig tmp = ((ColorPanelOrig)e.getSource());
		tmp.released();
		if (!seqItr.hasNext()) {
			scorePanel.incrScore();
			Thread computerTurn = new Thread(new threadComp()); //thread for computer turn
			computerTurn.run();
			//computerTurn();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

}
