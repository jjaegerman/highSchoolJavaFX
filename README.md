Here I present an assortment of fun high school coding side projects that I created while learning JavaFX, improving my OOP and discovering algorithms. These were all coded through 2017.

GameEngine:
  Game Engine is the first of the three. I was inspired by this online physics simulation game called Powder Game that I really enjoyed toying around with. I decided I wanted to make something approximate to real physics where objects could move around, interact with different elasticities, etc. I only worked up to something that appeared to follow gravity and collision physics with a few bugs. I quickly realized I didn't yet understand the kinetics during the collision and how to determine resultant component velocities or duration of collision. I used some kind of approximation that seemed realistic for elasticity constants I had invented. I had also planned to implement tethers, springs and axles which could be fixed to points on the screen. Other ideas were coulomb forces, electric and magnetic fields, gravity sources, etc. It would be interesting to complete this project accurately, now that I have more than enough knowledge. 

game:
  Game is the starts to a game engine (yes I know the names are deceiving; they show how the design deviated from my initial intentions). This game is 2-dimensional planar; the player has no ability to move in the z direction and the camera points down from on top. All I had implemented so far was a hierarchy of classes and a POV that follows the player. In the game, the player can move around with wasd and sprint with space. Walls are also generated randomly at the start. This was to be the starts to a highly adaptable game engine, but I stopped working on it because of a lack of game idea inspiration, and lost sight of the ultimate goal.

Mine5weep3r:
  For this project I first quickly coded up a game of classic Minesweeper. I then decided to code a machine learning AI for it without doing any research on machine learning algorithms, just as a challenge. The AI utilizes reinforcement learning by trying and failing and adjusting its weightings. The AI would start the game with an arbitrary click then iterates through all cleared spaces on the board from top left to bottom right, operating on squares of 9 tiles at a time, as is strategically optimal. Squares were encoded by listing tile values from top left to right and downwards where underscore denotes an uncleared tile, F denotes a flag (I implemented flags out of interest even though they aren't necessary) and numbers 0 through 8 denoting number of adjacent mines. There are thus 11^8 permutations, way too many for my implemented algorithm to realistically ever work with any training. For each permutation I wrote a text file (named with the mentioned encoding) where each line starts with a possible next permutation (what we consider changing it to) and is followed by a success constant that determines the odds of the algorithm choosing it. This success constant is altered at the end of the game by a function that relates to the number of tiles cleared in the given game; next permutations that immediatly result in the game ending are also immediatly given a success constant of 0. This never worked; that result is obvious now. I trained it for a few days, only for the slightest increase in performance.
