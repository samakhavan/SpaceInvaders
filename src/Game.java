/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	// set of screens the game can have
	private JPanel screens;
	private GameRoom room;
	private Highscores highscores;
	private GameOver gameover;
	
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Space Invaders XTREME");
        frame.setLocation(320, 100);
        frame.setResizable(false);
        
/*** GAME ****************************************************************************************/
        
        final JPanel game = new JPanel();
        game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
        
        // Reset button
        final JPanel control_panel = new JPanel();
        control_panel.setBackground(Color.BLACK);
        control_panel.setPreferredSize(new Dimension(780, 80));

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchToMenuScreen();
            }
        });
        control_panel.add(mainMenu);
        
        // info panel (contains score, lives, and high score)
        final JPanel info_panel = new JPanel();
        info_panel.setPreferredSize(new Dimension(780, 80));
        info_panel.setBackground(Color.BLACK);
        info_panel.setLayout(new BoxLayout(info_panel, BoxLayout.X_AXIS));
        info_panel.add(Box.createRigidArea(new Dimension(200, 0)));
        
        // add score to info panel
        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setPreferredSize(new Dimension(111, 80));
        scoreLabel.setForeground(Color.WHITE);
        info_panel.add(scoreLabel);
        info_panel.add(Box.createRigidArea(new Dimension(111, 0)));
        
        // add lives to info panel
        JLabel livesLabel = new JLabel("Lives: 3");
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setPreferredSize(new Dimension(111, 80));
        info_panel.add(livesLabel);
        info_panel.add(Box.createRigidArea(new Dimension(111, 0)));
        
        // add high score to info panel
        JLabel highScoreLabel = new JLabel("High Score: 0");
        highScoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setPreferredSize(new Dimension(111, 80));
        info_panel.add(highScoreLabel);
        info_panel.add(Box.createRigidArea(new Dimension(200,0)));
        
        // main playing area
        room = new GameRoom(this, scoreLabel, livesLabel);
        
        // add panels to game content pane
        game.add(info_panel);
        game.add(room);
        game.add(control_panel);
        
        // add game card to frame
        frame.add(game);
        
/*** MENU ****************************************************************************************/

        // main menu
        final Menu menu = new Menu(this);
        frame.add(menu);
   
/*** INSTRUCTIONS ********************************************************************************/
    
	    // instructions
	    final Instructions insn = new Instructions(this);
	    frame.add(insn);
	    
/*** HIGH SCORES *********************************************************************************/
	    File hsFile = new File("SIX_highscores.txt");
	    try {
	    	hsFile.createNewFile();
	    	highscores = new Highscores(this, "SIX_highscores.txt");
	    	frame.add(highscores);
	    } catch (IOException e) {
	    	System.err.println("ERROR: Couldn't read in high scores");
	    	e.printStackTrace();
	    }
	    
	    highScoreLabel.setText("High Score: " + highscores.getHighScore());
	    
/*** GAME OVER ***********************************************************************************/
	    try {
			gameover = new GameOver(this, "SIX_highscores.txt");
		} catch (IOException e) {
	    	System.err.println("ERROR: Couldn't initialize Game Over");
			e.printStackTrace();
		}
/*************************************************************************************************/
	    
	    // add the different game states to the card layout
	    screens = new JPanel(new CardLayout());
	    screens.add(menu, "menu");
	    screens.add(game, "game");
	    screens.add(insn, "insn");
	    screens.add(highscores, "highscores");
	    screens.add(gameover, "gameover");
	    
	    // add card layout to top level
	    frame.add(screens);
	
	    // Put the frame on the screen
	    frame.pack();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}
    
    // switches cards to show game screen, resets game
    public void switchToGameScreen() {
    	CardLayout currLayout = (CardLayout) screens.getLayout();
    	currLayout.show(screens, "game");
    	room.reset();
    }
    
    // switches cards to show menu screen
    public void switchToMenuScreen() {
    	CardLayout currLayout = (CardLayout) screens.getLayout();
    	currLayout.show(screens, "menu");
    }
    
    // switches cards to show instruction screen
    public void switchToInstructionScreen() {
    	CardLayout currLayout = (CardLayout) screens.getLayout();
    	currLayout.show(screens, "insn");
    }
    
    // switches cards to show high scores
    public void switchToHighScoresScreen() {
    	CardLayout currLayout = (CardLayout) screens.getLayout();
    	currLayout.show(screens, "highscores");
    }
    
    // switches cards to show game over screen
    public void switchToGameOverScreen() {
    	CardLayout currLayout = (CardLayout) screens.getLayout();
    	currLayout.show(screens, "gameover");
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}