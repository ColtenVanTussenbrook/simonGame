/**
 * Colten Van Tussenbrook
 * Chad Mano CS 2410 Java 
 * Simonish Supreme
 */

package components;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ScorePanelOrig extends JPanel {
	private static final String String = null;
	private JButton startBtn = new JButton("Start");
	private static int highScore;
	private static int score;
//	private int averageScore;
	private JLabel highScoreLabel;
	private JLabel scoreLabel;
	static ArrayList<highScoreDisplay> highScoresList = new ArrayList<highScoreDisplay>();
//	static ArrayList<String> namesList = new ArrayList<String>();
	public static String nameInput;
	public class highScoreDisplay{public highScoreDisplay() {}public String name; public int score;};
	
	/**
	 * constructor for score panel - creates panel and adds buttons
	 */
	
	public ScorePanelOrig() {
		scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
		highScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.CENTER);
		
		setLayout(new GridLayout(1, 3));
		
		add(scoreLabel);
		add(startBtn);
		add(highScoreLabel);		
	}
	
	/**
	 * method to get the start button
	 * @return start button
	 */
	
	public JButton getStartBtn() {
		return startBtn;
	}
	
	/**
	 * method to reset the score panel
	 */
	
	public void reset() {
		score = 0;
		scoreLabel.setText("Score: " + score);
		update(scoreLabel.getGraphics());
	}
	
	/**
	 * method increments score to see how many times the user has played
	 */
	
	public void incrScore() {
		score++;
		scoreLabel.setText("Score: " + score);
		update(scoreLabel.getGraphics());
		checkHighScore();
	}
	
	/**
	 * method checks the high score
	 */
	
	private void checkHighScore() {
		if (score > highScore) {
			highScore = score;
			highScoreLabel.setText("High Score: " + highScore);
			this.update(this.getGraphics());
		}
	}
	
	/**
	 * method gets the current score
	 * @return score
	 */
	
	public int getScore() {
		return score;
	}
	
	/**
	 * method gets the high score
	 * @return high score
	 */
	
	public static int getHighScore() {
		return highScore;
	}

	/**
	 * method that uses an ArrayList to store high scores + corresponding names
	 */
	
	public void highScores(){
		
		highScoreDisplay hs = new highScoreDisplay();
		hs.name = null;
		hs.score = score;
		
		highScoresList.add(hs);
		int index = highScoresList.size() -1;
		int index2 = index-1;
		String nameInput = JOptionPane.showInputDialog("Enter your name to see if you made the top ten list: ");
		hs.name = nameInput;
				
		while (highScoresList.get(index).score > highScoresList.get(index2).score) //sorts scores
		{	
			highScoreDisplay tmp = highScoresList.get(index);
			highScoresList.set(index, highScoresList.get(index2));
			highScoresList.set(index2, tmp);
			index--;
			index2--;
			if (index2 < 0)
			{
				break;
			}
		}
	}
	
	/**
	 * gets previous scores from previousScores.txt file - included in Simonish folder
	 */
	
	public void getPreviousScores(){
		BufferedReader br;
		try {
			br = new BufferedReader (new FileReader("previousScores.txt"));
			for (int i = 0; i < 10; i++)
			{
				String token[] = br.readLine().split(" ");
				highScoreDisplay hs = new highScoreDisplay();
				hs.name = token[0];
				hs.score = Integer.parseInt(token [1]);
				highScoresList.add(hs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * uses the .txt file to post numerical position, person and their score
	 * @return high score list
	 */
	
	public String getList(){
		String getHighScoreList = "";
		for (int i = 0; i < 10; i++)
		{
			getHighScoreList += i+1+"." + highScoresList.get(i).name + " " + highScoresList.get(i).score +"\n";
		}
		return getHighScoreList;				
	}		
}
