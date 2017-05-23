# SpaceInvaders

===================
=: Core Concepts :=
===================

1. 

Appropriately modeling state using 2-D arrays*

This design concept was not originally delineated in my proposal, but I realized that it would be a much better way
to represent the core block of enemies (as they very clearly visually represent a 2-D array). In addition, it made
instantiating the various enemies significantly cleaner--when I was previously using a Collection, I had to separately
create each of the 55 Aliens in the game, then add them all to the Collection one by one. This was an inefficient waste
of space. Now, I simply use a double for loop to populate the 2-D array, using the for loop indices to increment the
starting positions of each enemy.

2.

 Appropriately modeling state using collections

I originally intended to model all of the enemies with Collections, but as stated above, I decided against that. However,
I ended up using an ArrayList to model the special enemies (the red flying saucers). There are very few of these, so
instantiating the individual special enemies was not a problem. In addition, since they appear in a random order, having
the flexibility to remove/update elements of a Collection in place was very helpful. I also used a TreeMap to help store
the high scores and associate them with the user inputted name.

3.

 Using I/O to parse a novel file format

I created a Highscores class and a writeHighScores() method in the GameOver class that worked together to create a file
called "SIX_highscores.txt" and update it appropriately when the user finished a game and wanted to save their high score.
These were implemented using BufferedReaders and BufferedWriters, and they are designed to be error-free. The high score
file is formatted as follows: "(3 letter username), (highscore)". Any deviation from this pattern will "corrupt" that line
of the high score file, and the program will not read it in, thus running into no parsing/display errors.

4. Using inheritance/subtyping for dynamic dispatch

I created all of the ships in the game by modifying the GameObj class to create a new GameObject class, and then creating
a new abstract class Ship that extended GameObject while adding significant changes, such as implementing the weapons
(getBullet() and setBullet()), implementing the different scores (getDestroyScore() and setDestroyScore()), setting and
updating the animation speed (getAnimationSpeed() and setAnimationSpeed()), checking if a bullet existed (bulletExists()),
and an abstract attack() method that was different for each ship in the game. Since the ships are all relatively similar
(save the attack() method), subtyping was the perfect answer.

====================
=: Implementation :=
====================

Game: This is the main game class that implements Runnable. It creates the various screens that appear in the game and adds
      them all to an appropriate JFrame with a CardLayout.

GameRoom: Holds the main playing area of the game. Takes in user input and allows them to control the ship. Updates the game
	  every time the tick() method is called from a timer, checking collisions, updating movement and gamestate, and more.
	  Draws the appropriate game objects on the screen.

GameObject: An abstract class for every object in the game. Game Objects exist in the GameRoom. They have a position, velocity,
	    size, and bounds. Their velocity controls how they move; their position should always be within their bounds.

Bullet: The standard bullet that the user and enemies shoot. Extends GameObject and implements a new draw() method.

Ship: An abstract class for every ship in the game. Ships have a main method of attack (to be implemented by the specific ship).
      Each ship also has a single Bullet (to only allow one bullet to be shot at a time), a destroy score, and an animation speed.

Alien1: The first type of alien in the game. Extends Ship and implements a new attack() method that shoots a Bullet directly down.

Alien2: The second type of alien in the game. Extends Ship and implements a new attack() method that causes the alien to charge
	the player (switches from moving horizontally back and forth to moving directly at the player).

Alien3: The third type of alien in the game. Extends Ship and implements a randomized attack() method that shoots a Bullet either
	left or right randomly.

SpecialAlien: The fourth type of alien in the game. Extends Ship but overrides the move() method from GameObject to get rid of
	      movement clipping (as it starts and finishes off the screen). Has a trivial attack() method.

Instructions: Creates the instructions screen for the game. Has a button that returns to the Main Menu.

Menu: Creates the starting menu screen for the game. Has the title, a button for starting the game, a button for checking high
      scores, and a button for going to the instructions screen.

Highscores: Creates the high scores screen for the game. Reads in the high scores from a specially formatted text file, orders
	    them, and displays the top 10 appropriately on the screen. This screen is only updated each time the entire game is
	    restarted (closed and opened). Has a button to return to the Main Menu.

GameOver: Creates the game over screen for the game. Allows user to input their desired name to save the score they just
	  achieved--only saves the score if the format is appropriate (3 characters) and if they click the "Submit" button.
	  Has a button for restarting the game, and a button for going to the main menu.

Overall, my design is relatively good, but there could definitely be some improvements in the separation of functionality. I
did not fully plan out each part of the game from the beginning, and as I realized I needed more or less information from one
class or object, I simply changed them accordingly without refactoring the bigger picture. This led to rather convulted code
in some places and information that is not fully encapsulated/is represented in multiple places. If given the chance, I would
definitely refactor the code in the tick() method of the GameRoom class--there are several redundencies that could be fixed
given enough time to rethink the implementation and refactor the code. There were no severe stumbling blocks--creating the
appropriate File I/O for saving high scores across runs was a bit tough, but nothing that was too perplexing.

========================
=: External Resources :=
========================

Background image: https://spencer2124.files.wordpress.com/2014/06/background.png
Alien sprites: http://fontvir.us/ui/img/fonts/invaders.gif*
Player ship sprite: https://img.itch.io/aW1hZ2UvNTk0MjAvMjY3MzY5LnBuZw==/original/BAOyZX.png
JavaDocs: https://docs.oracle.com/javase/7/docs/api/

*I used this image as a reference and drew my own sprites using https://make8bitart.com/
