/** Tan Le (tle51), Tsz Lam (tlam22), Kevin Tang (ktang20)
  * CS 342 - Project 5: Tetris
  * T_shape class - Tetris shape outline
  */

interface T_shape{
  public static final int line = 1;
  public static final int square = 2;
  public static final int l_shape = 3;
  public static final int j_shape = 4;
  public static final int tee = 5;
  public static final int z_shape = 6;
  public static final int s_shape = 7;
  //Return the number correpsonding to each shape
  int the_shape();
  
  //Get the number of square used by shape ---> always 4 duh
  int shape_size();
  
  //Rotate shape to right from point of rotation, return the rotated points, for user to check for boundries
  XY[] rotate_right();
  
 //Rotate shape to left from point of rotation, return the rotated points, for user to check for boundries
  XY[] rotate_left();
  
  // return the cordinate of the squares
  // Use spawn position of grid as starting point/ reference, when 
  //creating cordinate of shape. Use the grid class to move shapes,
  // Just move the "current active" cordinate on grid accordingly
  // start col in 3-6 start row 0-3  (4x4 square)
  XY[] get_shape_cord();
  
  //Set the shape cordinates, if block was rotated, new cordinate should be updated using this
  void set_shape_cord(XY[] c_shape);
  
  //Move the shape one unit down on grid, need to check borders and conditions before placing on grid
   XY[] move_down_one();
  
}