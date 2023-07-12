# DiamondCircle, Faculty of Electrical Engineering, 2022

Application specification

The game is played on a grid with minimum dimensions of 7x7 and maximum dimensions of 10x10. The game can be played by a minimum of 2 and a maximum of 4 players. 
The matrix dimensions and the number of players are specified before launching the application, and user input is validated.

Each player has a unique name and possesses four figures of the same color. 
Each figure is characterized by its color and mode of movement. There are three types of figures: regular figures, floating figures, and super-fast figures. 
Each figure can move a specified number of squares, including both regular and floating figures. 
Super-fast figures can move twice the specified number of squares. 
Regular and super-fast figures can fall into a hole, while floating figures remain suspended above the hole. 
At the beginning of the game, each player is assigned four randomly chosen figures of the same color.

In addition to the figures used by the players, there is also a "ghost" figure. 
The ghost figure starts moving when the first player moves and lays bonus squares - diamonds - along its path. A random number of diamonds between 2 and the matrix dimensions is placed on random positions. The placement occurs every 5 seconds and continues until the end of the game. When a figure encounters a diamond, it "collects" it, and for the rest of the game, the number of squares it can move increases by the number of collected diamonds.

The figures move on the grid following a specific path, as shown in the provided image. In the given example, the starting position is the fourth square of the matrix. The order of the players is determined randomly, and they take turns making moves. A move is defined as moving a figure a certain number of squares from one position to another. When moving, if the target square is already occupied, the figure is placed on the next available square. The movement from one square to another takes one second. The mode of movement is determined by drawing a card randomly from a deck of 52 cards. There are regular cards and special cards. Regular cards consist of an image and the number of squares the figure can move. Special cards only display an image and create holes at certain positions on the path. The holes are black. After drawing a card, it is returned to the deck. If a figure falls into a hole, except for a floating figure, it is considered lost. In that case, if the player has more figures, they continue with the next one. The game ends when all players run out of figures - i.e., each of their figures reaches the goal (e.g., square 25 in the provided example) - or when all players have lost their figures. Information about the number of squares crossed and the movement time is stored for each figure. When displaying a figure on a square, both the color and type of the figure should be visible. The game can be paused and restarted. The game progresses automatically. At the end of the game, the results are saved in text files named IGRA_current_time.txt, following the format specified.
