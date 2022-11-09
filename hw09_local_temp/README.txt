=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: prmehra
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D arrays: I used a 2D array to hold my game state, with each cell within the array
  containing the number displayed by its respective tile. I iterate through the array
  every time I need to update the game or check the game state. The nature of
  the 2D array also allowed me to iterate through it in different ways. For example, I
  iterated through the array from top to bottom in one method, bottom to top in another,
  left to right in another, and right to left in another. I also encapsulated the array using
  the copy array method, which helped me when implementing the undo button. I stored ints
  in the array, which felt like the most suitable type considering the fact that the game
  logic of 2048 relies entirely on the numbers displayed on the tiles.

  2. Collections: I used a linked list, an implementation of the collection interface,
  to implement my undo button. I used a linked list as opposed to an array because I
  knew that the list would have to resize during game execution based on the number of
  turns played. It also allowed for unlimited undo functionality. In my linked list, I
  stored 2D arrays; each entry was a copy of the board after a turn was played. I accessed
  elements by removing and getting the last element in the list when the undo button was
  called. Both an ArrayList and a LinkedList could have worked given my use case, but I
  decided upon a LinkedList because it is marginally faster when manipulating data.

  3. File I/O: I used File I/O to implement my save and resume functionality. When the save
  button is clicked, the current game state is written to a file SaveGame.txt. When the
  resume button is clicked, it will restore the board to the most recently saved state.
  I decided to use File I/O as opposed to a class variable because writing to an external
  file allows me to save across instances of the game. So if a player wants to totally exit
  the game and open it again later, they can resume where they left off.

  4. JUnit Testable Component: My Twenty48 class models the core-game state without
  relying on the GUI, so I used JUnit testing to test my methods within that class. I
  also created a method within my Twenty48 class that allowed me to create a test board,
  which allowed me to rely a little less on the random nature of initializing the board.
  This also allowed me to test the edge cases of updating the board in every direction.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Twenty48.java - Models the core game state. Deals with updating and checking the board.
  Also holds functions that deal with the functionality of the undo, save, and resume
  buttons.

  GameBoard.java - Creates an instance of the Twenty48 class, using it as a model. It also
  acts as the controller with the KeyListener. It also deals with the repainting of the board.

  RunTwenty48.java - Creates an instance of the GameBoard class and implements a bit of top-
  level controller functionality by creating buttons.

  Game.java - Initializes the RunTwenty48 class and runs the game.

  GameTest.java - Used for all JUnit testing.

  SaveGame.txt - Not a class, but is the file used for all File I/O functionality.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  The biggest blocks when implementing my game were definitely all the bugs
  encountered when designing the game logic. I had to constantly redesign
  as I tested more and more and discovered edge cases that my code didn't cover.
  For example, I had to change the way I iterated through my game board a few times
  as I realized I wasn't checking corners, vertically adjacent tiles located on the
  edges, etc.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think there is a good separation of functionality as everything follows the
  Model-View-Controller framework. In addition, I encapsulated the game board
  to ensure that functionality stayed consistent. I would potentially refactor
  my update methods in the Twenty48 class. They were all slightly different, so I
  couldn't find a way to create a helper method that would apply to all of them.
  I do think that, given more time, I could have found a way to make it more efficient.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  N/A
