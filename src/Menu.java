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
public class Menu extends JPanel {
	// menu constants
    public static final int MENU_WIDTH = 780;
    public static final int MENU_HEIGHT = 830;
    private final Game game;
	
	// constructor
	public Menu (Game currGame) {
		this.game = currGame;
		
		// create Start button
        final JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.switchToGameScreen();
            }
        });
        
		// create Instructions button
        final JButton insn = new JButton("Instructions");
        insn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.switchToInstructionScreen();
            }
        });
        
		// create High Scores button
        final JButton highscores = new JButton("High Scores");
        highscores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.switchToHighScoresScreen();
            }
        });
        
        // create title
        final JLabel title = new JLabel("Space Invaders");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Serif", Font.BOLD, 80));
        // create subtitle
        final JLabel subtitle = new JLabel("XTREME");
		subtitle.setForeground(Color.WHITE);
		subtitle.setFont(new Font("Serif", Font.BOLD, 80));
        
		final JPanel layout = new JPanel();
		layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
		layout.setBackground(Color.BLACK);
		layout.setPreferredSize(this.getPreferredSize());
		title.setAlignmentX(CENTER_ALIGNMENT);
		subtitle.setAlignmentX(CENTER_ALIGNMENT);
		start.setAlignmentX(CENTER_ALIGNMENT);
		insn.setAlignmentX(CENTER_ALIGNMENT);
		highscores.setAlignmentX(CENTER_ALIGNMENT);
		layout.add(Box.createRigidArea(new Dimension(0, 200)));
		layout.add(title);
		layout.add(subtitle);
		layout.add(Box.createRigidArea(new Dimension(0, 200)));
		layout.add(start);
		layout.add(Box.createRigidArea(new Dimension(0, 10)));
		layout.add(insn);
		layout.add(Box.createRigidArea(new Dimension(0, 10)));
		layout.add(highscores);
        
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
        return new Dimension(MENU_WIDTH, MENU_HEIGHT);
    }
}
