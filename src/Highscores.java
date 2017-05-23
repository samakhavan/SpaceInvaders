/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

/**
 * GameRoom
 * 
 * This class holds the primary game logic for how different objects interact with one another.
 * Contains a timer that interacts with the different methods and repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class Highscores extends JPanel {
	// highscores constants
    public static final int HS_WIDTH = 780;
    public static final int HS_HEIGHT = 830;
    private final Game game;
    private final String file;
	private BufferedReader reader;
	private Map<Integer, String> highscores;
	private List<Integer> sortedScores;
	
	// constructor
	public Highscores (Game currGame, String filename) throws IOException {
		this.game = currGame;
		this.file = filename;
		highscores = new TreeMap<Integer, String>();
		
		// attempt to read scores from specified file
		try {
			reader = new BufferedReader(new FileReader(file));
		// catch exceptions for badly formatted input
		} catch (IOException e) {
			System.err.println("ERROR: Could not read high scores from file");
			reader.close();
		}
		
		String currLine = reader.readLine();
		
		// iterate through BufferedReader until end of file
		while (currLine != null) {
			// format is comma separated
			if (!currLine.contains(",")) {
				currLine = reader.readLine();
				continue;
			}
			String[] hs = currLine.split(",");
			// if there is more than 1 comma, bad format
			if (hs.length != 2) {
				currLine = reader.readLine();
				continue;
			}
			
			String name = hs[0].trim();
			String scoreString = hs[1].trim();
			// if either argument is just whitespace, skip
			if (name.isEmpty() || scoreString.isEmpty()) {
				currLine = reader.readLine();
				continue;
			}
			
			// if name is too short/long, skip
			if (name.length() != 3) {
				currLine = reader.readLine();
				continue;
			}
			
			int score = 0;
			// if second argument is not a number, skip
			try {
				score = Integer.parseInt(scoreString);
			} catch (NumberFormatException e) {
				currLine = reader.readLine();
				continue;
			}
			highscores.put(score, name);
			
			currLine = reader.readLine();
		}
		reader.close();
		
		String top10 = "";
		int rank = 1;
		// add formatted high scores to string
		sortedScores = new ArrayList<Integer>(highscores.keySet());
		Collections.sort(sortedScores, Collections.reverseOrder());
		for (Integer entry : sortedScores) {
			top10 += (rank + ". " + highscores.get(entry) + ", " + entry + "<br>");
			rank++;
			// only display top 10 scores
			if (rank > 10) { break; }
		}
		
		final JLabel hsText =
			new JLabel("<html><div style='text-align: center;'>" + "High Scores<br><br>" + top10 +
					   "</div><html>", (int) CENTER_ALIGNMENT);
		hsText.setForeground(Color.WHITE);
		hsText.setFont(new Font("Serif", Font.BOLD, 22));
        
		// create Main Menu button
        final JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.switchToMenuScreen();
            }
        });
        
        // create layout of High Scores screen
        final JPanel layout = new JPanel();
        layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
        layout.setPreferredSize(new Dimension(HS_WIDTH, HS_HEIGHT));
        layout.setBackground(Color.BLACK);
        
        // add text and buttons to instructions panel
		hsText.setAlignmentX(CENTER_ALIGNMENT);
		mainMenu.setAlignmentX(CENTER_ALIGNMENT);
		layout.add(Box.createRigidArea(new Dimension(0, 200)));
        layout.add(hsText);
        layout.add(Box.createRigidArea(new Dimension(0, 100)));
        layout.add(mainMenu);
        
        this.add(layout);
	}
	
	// get highest score overall
	public int getHighScore() {
		if (sortedScores.isEmpty()) {
			return 0;
		}
		else {
			return sortedScores.get(0);
		}
	}
	
	// put mouse focus in menu
	public void reset() {
		requestFocusInWindow();
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(HS_WIDTH, HS_HEIGHT);
    }
}