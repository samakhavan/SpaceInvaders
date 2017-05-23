/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * GameRoom
 * 
 * This class holds the primary game logic for how different objects interact with one another.
 * Contains a timer that interacts with the different methods and repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class Instructions extends JPanel {
	// instruction constants
    public static final int INSN_WIDTH = 780;
    public static final int INSN_HEIGHT = 830;
    private final Game game;
	
	// constructor
	public Instructions (Game currGame) {
		this.game = currGame;
		
		final JLabel insnText =
			new JLabel("<html><div style='text-align: center;'>" + "Welcome to Space " +
					   "Invaders XTREME!<br><br>This game plays just like the typical game " +
					   "you're probably used to.<br>Control your ship by using the Left and " +
					   "Right arrow keys to move side to side.<br>Pressing [SPACE] shoots--" +
					   "remember, you can only shoot one bullet at a time!<br>The green aliens " +
					   "are worth 10 points.<br>The purple aliens are worth 20 points.<br>The " +
					   "orange aliens are worth 40 points.<br>The red alien saucers are special." +
					   " No one knows how much they're worth!<br><br>If you get hit by a laser, " +
					   "you lose a life.<br>If an alien makes it past the front of your ship, " +
					   "you lose a life.<br><br>If you lose all three lives, you die!</div><html>",
					   (int) CENTER_ALIGNMENT);
		insnText.setForeground(Color.WHITE);
		insnText.setFont(new Font("Serif", Font.BOLD, 22));
        
		// create Main Menu button
        final JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.switchToMenuScreen();
            }
        });
        
        final JPanel layout = new JPanel();
        layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
        layout.setPreferredSize(new Dimension(INSN_WIDTH, INSN_HEIGHT));
        layout.setBackground(Color.BLACK);
        
        // add text and buttons to instructions panel
		insnText.setAlignmentX(CENTER_ALIGNMENT);
		mainMenu.setAlignmentX(CENTER_ALIGNMENT);
		layout.add(Box.createRigidArea(new Dimension(0, 200)));
        layout.add(insnText);
        layout.add(Box.createRigidArea(new Dimension(0, 100)));
        layout.add(mainMenu);
        
        this.add(layout);
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
        return new Dimension(INSN_WIDTH, INSN_HEIGHT);
    }
}