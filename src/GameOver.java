/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

/**
 * GameRoom
 * 
 * This class holds the primary game logic for how different objects interact with one another.
 * Contains a timer that interacts with the different methods and repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameOver extends JPanel {
	// menu constants
    public static final int GO_WIDTH = 780;
    public static final int GO_HEIGHT = 830;
    private final Game game;
    private String file;
    private BufferedWriter writer;
    private String username;
	
	// constructor
	public GameOver (Game currGame, String filename) throws IOException {
		this.game = currGame;
		this.file = filename;
		
		// create End Text label
		final JLabel endingText = new JLabel("Game Over!");
		endingText.setForeground(Color.WHITE);
		endingText.setFont(new Font("Serif", Font.BOLD, 72));
		
		// create Play Again button
        final JButton playAgain = new JButton("Play Again");
        playAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.switchToGameScreen();
            }
        });
        
		// create Main Menu button
        final JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.switchToMenuScreen();
            }
        });
        
        final JPanel buttons = new JPanel();
        buttons.add(mainMenu);
        buttons.add(playAgain);
        buttons.setBackground(Color.BLACK);
        
        // create username input text box for high score
        final JLabel insn = new JLabel("Enter Your Name (can only be 3 letters):");
        insn.setForeground(Color.WHITE);
        
        final JTextArea inputText = new JTextArea();
        inputText.setBackground(Color.WHITE);
        inputText.setForeground(Color.BLACK);
        inputText.setPreferredSize(new Dimension(200, 20));
        
        // create Submit button to send high score to file
        final JButton submit = new JButton("Submit (Only after typing name)");
        submit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		username = inputText.getText();
        		try {
					writeHighScore();
				} catch (IOException exception) {
					System.err.println("ERROR: Couldn't write high score");
				}
        		inputText.setText("");
        	}
        });
        
        final JPanel layout = new JPanel();
		layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
		layout.setBackground(Color.BLACK);
		layout.setPreferredSize(this.getPreferredSize());
		endingText.setAlignmentX(CENTER_ALIGNMENT);
		insn.setAlignmentX(CENTER_ALIGNMENT);
		inputText.setAlignmentX(CENTER_ALIGNMENT);
		submit.setAlignmentX(CENTER_ALIGNMENT);
		buttons.setAlignmentX(CENTER_ALIGNMENT);
		layout.add(Box.createRigidArea(new Dimension(0, 30)));
		layout.add(endingText);
		layout.add(Box.createRigidArea(new Dimension(0, 10)));
		layout.add(insn);
		layout.add(Box.createRigidArea(new Dimension(0, 10)));
		layout.add(inputText);
		layout.add(Box.createRigidArea(new Dimension(0, 10)));
		layout.add(submit);
		layout.add(Box.createRigidArea(new Dimension(0, 10)));
		layout.add(buttons);
		layout.add(Box.createRigidArea(new Dimension(0, 10)));
		
		this.add(layout);
	}
	
	private void writeHighScore() throws IOException {
		// if user doesn't input username, do nothing
		if (username == null) { return; }
		
		// attempt to write to high score file
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			System.err.println("ERROR: Couldn't write to high score file");
			writer.close();
		}
		writer.newLine();
		writer.write(username + ", " + GameRoom.totalScore);
		writer.newLine();
		writer.close();
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
        return new Dimension(GO_WIDTH, GO_HEIGHT);
    }
}