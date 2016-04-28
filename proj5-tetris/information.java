/** Tan Le (tle51), Tsz Lam (tlam22), Kevin Tang (ktang20)
  * CS 342 - Project 5: Tetris
  * information class - Keep track of the state of each grid cells
  */

import javax.swing.*; 
class information extends JLabel{
 
    public boolean inused;
    public boolean moving_piece;
    public information(String text, int num){
     super(text, num); 
    }
  }